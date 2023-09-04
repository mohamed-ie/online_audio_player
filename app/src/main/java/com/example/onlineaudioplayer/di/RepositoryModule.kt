package com.example.onlineaudioplayer.di

import com.example.onlineaudioplayer.feature.data.repository.ChannelsRepositoryImpl
import com.example.onlineaudioplayer.feature.domian.data.repository.ChannelsRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    single<ChannelsRepository> { ChannelsRepositoryImpl(get(), Dispatchers.IO) }
}