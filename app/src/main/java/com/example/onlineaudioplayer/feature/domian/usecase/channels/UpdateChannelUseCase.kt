package com.example.onlineaudioplayer.feature.domian.usecase.channels

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.domian.data.repository.ChannelsRepository

class UpdateChannelUseCase(private val repository: ChannelsRepository)  {
    suspend operator fun invoke(
        channel: RadioChannel
    ) = repository.updateChannel(channel)
}