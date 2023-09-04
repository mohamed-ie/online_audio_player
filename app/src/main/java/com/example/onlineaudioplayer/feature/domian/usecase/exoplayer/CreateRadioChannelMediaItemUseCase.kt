package com.example.onlineaudioplayer.feature.domian.usecase.exoplayer

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel

class CreateRadioChannelMediaItemUseCase {
    operator fun invoke(channel: RadioChannel): MediaItem {
        val metadata = MediaMetadata.Builder()
            .setTitle(channel.name)
            .setDescription(channel.description)
            .build()

        return MediaItem.Builder()
            .setUri(channel.url)
            .setMediaMetadata(metadata)
            .build()
    }
}