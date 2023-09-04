package com.example.onlineaudioplayer.feature.domian.usecase.channels

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.domian.data.repository.ChannelsRepository

class DeleteChannelUseCase(private val repository: ChannelsRepository)  {
    suspend operator fun invoke(channel: RadioChannel) =
        repository.deleteChannel(channel)
}