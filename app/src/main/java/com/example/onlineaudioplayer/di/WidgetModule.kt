package com.example.onlineaudioplayer.di

import com.example.onlineaudioplayer.glance.AppWidget
import org.koin.dsl.module

val widgetModule = module(createdAtStart = false) {
    factory { AppWidget(get(), get(), get(), get()) }
}