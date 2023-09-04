package com.example.onlineaudioplayer.glance

import android.content.Context
import android.util.LayoutDirection
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.text.layoutDirection
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.domian.usecase.channels.GetChannelsUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.channels.UpdateChannelUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.exoplayer.CreateRadioChannelMediaItemUseCase
import com.example.onlineaudioplayer.glance.view.WidgetContent
import com.example.onlineaudioplayer.glance.view.WidgetEvent
import com.example.onlineaudioplayer.glance.view.WidgetState
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AppWidget(
    private val mediaController: ListenableFuture<MediaController>,
    private val getChannels: GetChannelsUseCase,
    private val createMediaItem: CreateRadioChannelMediaItemUseCase,
    private val updateChannelUseCase: UpdateChannelUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : GlanceAppWidget() {
    private lateinit var handler: Handler
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val isRtl =
            AppCompatDelegate.getApplicationLocales()[0]?.layoutDirection == LayoutDirection.RTL

        provideContent {
            handler = Handler(rememberCoroutineScope())
            handler.listenToChannels()
            GlanceTheme {
                val scope = rememberCoroutineScope()
                WidgetContent(
                    state = handler.state.collectAsState().value,
                    isRtl = isRtl,
                    onEvent = handler::onEvent,
                )
            }
        }
    }

    override suspend fun onDelete(context: Context, glanceId: GlanceId) {
        super.onDelete(context, glanceId)
        handler.clear()
    }

    private inner class Handler(private val scope: CoroutineScope) {
        private val _state = MutableStateFlow(WidgetState())
        val state = _state.asStateFlow()
        private var stop: (() -> Unit)? = null
        private var pause: (() -> Unit)? = null
        private var play: ((MediaItem?) -> Unit)? = null
        private var removePlayerListener: (() -> Unit)? = null

        private val playerListener = object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                _state.update { it.copy(playbackCardVisible = false) }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                _state.update { it.copy(isPlaying = isPlaying) }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                _state.update {
                    it.copy(
                        isLoading = playbackState == Player.STATE_BUFFERING,
                        playbackCardVisible = playbackState != Player.STATE_IDLE
                    )
                }
            }

            override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                super.onMediaMetadataChanged(mediaMetadata)
                _state.update {
                    it.copy(
                        name = mediaMetadata.title?.toString() ?: "",
                        description = mediaMetadata.description?.toString() ?: ""
                    )
                }
            }
        }

        init {
            listenToMediaController()
        }

        fun listenToMediaController() {
            mediaController.addListener({
                mediaController.get().apply {
                    addListener(playerListener)

                    updatePlayback(currentMediaItem, isPlaying)

                    this@Handler.removePlayerListener = {
                        removeListener(playerListener)
                    }

                    this@Handler.play = { mediaItem ->
                        if (mediaItem != null) {
                            setMediaItem(mediaItem)
                            prepare()
                        }
                        play()
                    }

                    this@Handler.pause = this::pause

                    this@Handler.stop = this::stop
                }
            }, MoreExecutors.directExecutor())
        }

        private fun updatePlayback(mediaItem: MediaItem?, isPlaying: Boolean) {
            _state.update {
                it.copy(
                    isPlaying = isPlaying,
                    playbackCardVisible = isPlaying || mediaItem != null,
                    name = mediaItem?.mediaMetadata?.title?.toString() ?: "",
                    description = mediaItem?.mediaMetadata?.description?.toString() ?: ""
                )
            }
        }

        fun onEvent(event: WidgetEvent) {
            when (event) {
                is WidgetEvent.Play ->
                    play?.invoke(event.channel?.let { createMediaItem(it) })

                WidgetEvent.Pause ->
                    pause?.invoke()

                WidgetEvent.Stop ->
                    stop?.invoke()

                is WidgetEvent.FavoriteChanged ->
                    toggleFavorite(event.channel)
            }
        }

        private fun toggleFavorite(channel: RadioChannel) = scope.launch(defaultDispatcher) {
            updateChannelUseCase(channel.copy(isFavorite = !channel.isFavorite))
        }

        fun listenToChannels() {
            getChannels()
                .onEach { channels -> _state.update { it.copy(channels = channels) } }
                .launchIn(scope)
        }

        fun clear() {
            removePlayerListener?.invoke()
            mediaController.cancel(true)
        }
    }
}