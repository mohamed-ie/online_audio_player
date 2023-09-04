package com.example.onlineaudioplayer.feature.domian.data.repository

import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository {
    val channels: Flow<List<RadioChannel>>
    suspend fun addChannel(channel: RadioChannel)
    suspend fun deleteChannel(channel: RadioChannel)
    suspend fun updateChannel(channel: RadioChannel)
}