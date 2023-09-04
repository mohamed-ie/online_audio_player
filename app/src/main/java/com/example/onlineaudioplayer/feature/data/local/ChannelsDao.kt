package com.example.onlineaudioplayer.feature.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelsDao {
    @Insert
    suspend fun insert(channel: RadioChannel)

    @get:Query("SELECT * FROM channel ORDER BY is_favorite DESC")
    val channels: Flow<List<RadioChannel>>

    @Delete
    suspend fun delete(channel: RadioChannel)

    @Update
    suspend fun update(channel: RadioChannel)
}