package com.example.onlineaudioplayer.di

import androidx.room.Room
import com.example.onlineaudioplayer.feature.data.local.AppDatabase
import com.example.onlineaudioplayer.feature.data.local.AppDatabaseCallback
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).addCallback(AppDatabaseCallback())
            .build()
            .channelDao
    }
}