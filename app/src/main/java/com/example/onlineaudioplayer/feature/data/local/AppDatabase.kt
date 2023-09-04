package com.example.onlineaudioplayer.feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onlineaudioplayer.feature.data.local.entity.RadioChannel

@Database(
    entities = [RadioChannel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        const val DATABASE_NAME = "app_db"
    }
    abstract val channelDao: ChannelsDao
}