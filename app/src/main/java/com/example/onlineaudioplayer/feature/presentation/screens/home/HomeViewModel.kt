package com.example.onlineaudioplayer.feature.presentation.screens.home

import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.core.base.BaseScreenViewModel
import com.example.onlineaudioplayer.core.utils.UIText
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.domian.usecase.channels.AddChannelUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.channels.DeleteChannelUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.channels.GetChannelsUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.channels.UpdateChannelUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.exoplayer.CreateRadioChannelMediaItemUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.locale.ChangeLocaleUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.locale.GetCurrentLocaleUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.validator.ChannelNameValidatorUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.validator.ChannelUrlValidatorUseCase
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.HomeEvent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.HomeState
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.HomeUIEvent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.component.channel.ChannelEvent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.add_channel.AddChannelDialogState
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.add_channel.AddChannelEvent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.delete_channel.ConfirmDialogEvent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.delete_channel.DeleteChannelDialogState
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.languages.LanguageDialogEvent
import com.example.onlineaudioplayer.feature.presentation.screens.home.view.dialog.languages.LanguageDialogState
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val channels: GetChannelsUseCase,
    private val addChannelsUseCase: AddChannelUseCase,
    private val deleteChannelUseCase: DeleteChannelUseCase,
    private val updateChannelUseCase: UpdateChannelUseCase,
    private val channelNameValidatorUseCase: ChannelNameValidatorUseCase,
    private val channelUrlValidatorUseCase: ChannelUrlValidatorUseCase,
    private val changeLocaleUseCase: ChangeLocaleUseCase,
    private val getCurrentLocaleUseCase: GetCurrentLocaleUseCase,
    private val createRadioChannelMediaItemUseCase: CreateRadioChannelMediaItemUseCase,
    private val mediaController: ListenableFuture<MediaController>
) : BaseScreenViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _languageDialogState = MutableStateFlow(LanguageDialogState())
    val languageDialogState = _languageDialogState.asStateFlow()

    private val _deleteChannelDialogState = MutableStateFlow(DeleteChannelDialogState())
    val deleteDialogState = _deleteChannelDialogState.asStateFlow()

    private val _addChannelDialogState = MutableStateFlow(AddChannelDialogState())
    val addChannelDialogState = _addChannelDialogState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var editedChannel: RadioChannel? = null

    private var stop: (() -> Unit)? = null
    private var pause: (() -> Unit)? = null
    private var play: ((RadioChannel?) -> Unit)? = null
    private var removePlayerListener: (() -> Unit)? = null

    private val playerListener = object : Player.Listener {

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            handlePlayException(error)
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
                    bottomBarVisible = playbackState != Player.STATE_IDLE
                )
            }
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onMediaMetadataChanged(mediaMetadata)
            _state.update {
                it.copy(
                    playbackTitle = mediaMetadata.title?.toString() ?: "",
                    playbackDescription = mediaMetadata.description?.toString() ?: ""
                )
            }
        }
    }

    private fun handlePlayException(exception: PlaybackException) {
        val error = when (exception.errorCode) {
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT,
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED ->
                UIText.StringResource(R.string.network_error)

            PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS ->
                UIText.StringResource(R.string.url_error)

            else -> exception.localizedMessage?.let { UIText.DynamicString(it) }
                ?: UIText.StringResource(R.string.unexpected_error_occurred)

        }

        viewModelScope.launch { _uiEvent.emit(HomeUIEvent.ShowError(error)) }
    }

    init {
        listenToChannels()
        addMediaControllerListener()
    }

    private fun listenToChannels() = channels()
        .onEach { channels ->
            toStableScreenState()
            _state.update { it.copy(channels = channels) }
        }.launchIn(viewModelScope)

    private fun addMediaControllerListener() {
        mediaController.addListener({
            mediaController.get().apply {
                updatePlayback(currentMediaItem, isPlaying)

                addListener(playerListener)

                removePlayerListener = {
                    removeListener(playerListener)
                }

                this@HomeViewModel.play = { channel ->
                    if (channel != null) {
                        setMediaItem(createRadioChannelMediaItemUseCase(channel))
                        prepare()
                    }
                    play()
                }

                this@HomeViewModel.pause = this::pause

                this@HomeViewModel.stop = this::stop

            }
        }, MoreExecutors.directExecutor())
    }

    private fun updatePlayback(mediaItem: MediaItem?, isPlaying: Boolean) {
        _state.update {
            it.copy(
                isPlaying = isPlaying,
                bottomBarVisible = isPlaying || mediaItem != null,
                playbackTitle = mediaItem?.mediaMetadata?.title?.toString() ?: "",
                playbackDescription = mediaItem?.mediaMetadata?.description?.toString() ?: ""
            )
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ShowAddChannelDialog ->
                _addChannelDialogState.update { AddChannelDialogState(visible = true) }

            HomeEvent.Pause ->
                pause?.invoke()

            HomeEvent.Play ->
                play?.invoke(null)

            HomeEvent.Stop ->
                stop?.invoke()


            HomeEvent.ShowLanguageDialog ->
                _languageDialogState.update {
                    it.copy(
                        visible = true,
                        selected = getCurrentLocaleUseCase()?.toLanguageTag() ?: "en"
                    )
                }
        }
    }


    fun onChannelEvent(event: ChannelEvent) {
        when (event) {
            is ChannelEvent.Play ->
                play?.invoke(event.channel)

            is ChannelEvent.FavoriteToggle ->
                toggleFavorite(event.channel)

            is ChannelEvent.ShowDeleteDialog ->
                _deleteChannelDialogState.update {
                    DeleteChannelDialogState(
                        visible = true,
                        channel = event.channel
                    )
                }

            is ChannelEvent.ShowEditDialog -> with(event.channel) {
                editedChannel = this
                _addChannelDialogState.update {
                    it.copy(
                        visible = true,
                        editMode = true,
                        name = name,
                        description = description,
                        url = url
                    )
                }
            }
        }
    }

    private fun toggleFavorite(channel: RadioChannel) = viewModelScope.launch {
        updateChannelUseCase(channel.copy(isFavorite = !channel.isFavorite))
    }

    fun onAddChannelDialogEvent(event: AddChannelEvent) {
        when (event) {
            is AddChannelEvent.NameChanged ->
                _addChannelDialogState.update { it.copy(name = event.value) }

            is AddChannelEvent.DescriptionChanged ->
                _addChannelDialogState.update { it.copy(description = event.value) }

            is AddChannelEvent.UrlChanged ->
                _addChannelDialogState.update { it.copy(url = event.value) }

            AddChannelEvent.Dismiss ->
                dismissAddChannelDialog()

            AddChannelEvent.Save ->
                saveChannel()

        }
    }

    private fun dismissAddChannelDialog() {
        _addChannelDialogState.update { it.copy(visible = false) }
        editedChannel = null
    }

    private fun saveChannel() {
        val editMode = addChannelDialogState.value.editMode
        validateChannelData(editMode)
        if (!isChannelDataValid())
            return
        if (editMode)
            updateChannel()
        else
            addChannel()
        dismissAddChannelDialog()
    }

    private fun validateChannelData(editMode: Boolean) {
        val channels =
            _state.value.channels.run { if (editMode) filter { it != editedChannel } else this }
        val (nameError, nameErrorMessage) = channelNameValidatorUseCase(
            addChannelDialogState.value.name,
            channels
        )
        val (urlError, urlErrorMessage) = channelUrlValidatorUseCase(
            addChannelDialogState.value.url,
            channels
        )
        _addChannelDialogState.update {
            it.copy(
                nameError = !nameError,
                nameErrorMessage = nameErrorMessage,
                urlError = !urlError,
                urlErrorMessage = urlErrorMessage
            )
        }
    }

    private fun isChannelDataValid(): Boolean = addChannelDialogState.value.run {
        !nameError && !urlError
    }

    private fun updateChannel() = viewModelScope.launch {
        with(addChannelDialogState.value) {
            val channel = editedChannel?.copy(
                name = name,
                url = url,
                description = description
            ) ?: return@launch
            updateChannelUseCase(channel)
        }
    }

    private fun addChannel() = viewModelScope.launch {
        with(addChannelDialogState.value) {
            addChannelsUseCase(name = name, url = url, description = description)
        }
    }

    fun onDeleteDialogEvent(event: ConfirmDialogEvent) {
        when (event) {
            is ConfirmDialogEvent.Confirm -> {
                deleteChannel()
                _deleteChannelDialogState.update { DeleteChannelDialogState() }
            }

            ConfirmDialogEvent.Dismiss ->
                _deleteChannelDialogState.update { it.copy(visible = false) }

        }
    }

    private fun deleteChannel() {
        val channel = deleteDialogState.value.channel ?: return
        viewModelScope.launch { deleteChannelUseCase(channel) }
    }

    fun onLanguageDialogEvent(event: LanguageDialogEvent) {
        when (event) {
            LanguageDialogEvent.Dismiss ->
                _languageDialogState.update { it.copy(visible = false) }

            is LanguageDialogEvent.LanguageChanged ->
                _languageDialogState.update {
                    it.copy(
                        selected = event.language,
                        isSaveEnabled = (getCurrentLocaleUseCase()?.language
                            ?: "en") != event.language
                    )
                }

            LanguageDialogEvent.Save -> {
                changeLocaleUseCase(_languageDialogState.value.selected)
                _languageDialogState.update { LanguageDialogState() }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        removePlayerListener?.invoke()
        mediaController.cancel(true)
    }
}