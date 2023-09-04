package com.example.onlineaudioplayer

import android.app.Application
import com.example.onlineaudioplayer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@MainApplication)
            // declare modules
            modules(appModule)
        }
    }
}