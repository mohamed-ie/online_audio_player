package com.example.onlineaudioplayer.di

import org.koin.dsl.module

val appModule = module {
    includes(roomModule)
    includes(repositoryModule)
    includes(useCasesModule)
    includes(playbackModule)
    includes(homeModule)
    includes(widgetModule)
}