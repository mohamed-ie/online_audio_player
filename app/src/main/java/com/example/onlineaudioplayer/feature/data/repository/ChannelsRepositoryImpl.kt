package com.example.onlineaudioplayer.feature.data.repository

import com.example.onlineaudioplayer.feature.data.local.ChannelsDao
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import com.example.onlineaudioplayer.feature.domian.data.repository.ChannelsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ChannelsRepositoryImpl(
    private val dao: ChannelsDao,
    private val ioDispatcher: CoroutineDispatcher
) : ChannelsRepository {
    override val channels: Flow<List<RadioChannel>> =
        dao.channels.flowOn(ioDispatcher)

    override suspend fun addChannel(channel: RadioChannel) =
        withContext(ioDispatcher) {
            dao.insert(channel)
        }

    override suspend fun deleteChannel(channel: RadioChannel) =
        withContext(ioDispatcher) {
            dao.delete(channel)
        }

    override suspend fun updateChannel(channel: RadioChannel) =
        withContext(ioDispatcher) {
            dao.update(channel)
        }
}
