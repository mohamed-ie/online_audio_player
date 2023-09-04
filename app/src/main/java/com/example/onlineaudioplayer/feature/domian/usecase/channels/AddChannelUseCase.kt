package com.example.onlineaudioplayer.feature.domian.usecase.channels

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.domian.data.repository.ChannelsRepository

class AddChannelUseCase(private val repository: ChannelsRepository) {
    suspend operator fun invoke(name: String, description: String, url: String) =
        repository.addChannel(RadioChannel(name = name.trim(), description = description.trim(), url = url.trim()))
}