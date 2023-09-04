package com.example.onlineaudioplayer.feature.domian.usecase.channels

import com.example.onlineaudioplayer.feature.domian.data.repository.ChannelsRepository

class GetChannelsUseCase(private val repository: ChannelsRepository)  {
    operator fun invoke() = repository.channels
}