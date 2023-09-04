package com.example.onlineaudioplayer.glance.view

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.glance.view.component.ChannelCard
import com.example.onlineaudioplayer.glance.view.component.PlaybackCard


@Composable
fun WidgetContent(state: WidgetState, isRtl: Boolean = false, onEvent: (WidgetEvent) -> Unit) =
    with(state) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ImageProvider(R.drawable.background_widget))
        ) {
            LazyColumn(
                modifier = GlanceModifier.fillMaxWidth().defaultWeight()
            ) {
                items(channels, itemId = RadioChannel::id) { channel ->
                    ChannelCard(
                        channel = channel,
                        isRtl = isRtl,
                        onClick = { onEvent(WidgetEvent.Play(channel)) },
                        onFavorite = { onEvent(WidgetEvent.FavoriteChanged(channel)) }
                    )
                }
            }
            if (playbackCardVisible)
                PlaybackCard(
                    name = name,
                    description = description,
                    isPlaying = isPlaying,
                    isLoading = isLoading,
                    isRtl = isRtl,
                    onPlay = { onEvent(WidgetEvent.Play()) },
                    onPause = { onEvent(WidgetEvent.Pause) },
                    onStop = { onEvent(WidgetEvent.Stop) }
                )
        }
    }
