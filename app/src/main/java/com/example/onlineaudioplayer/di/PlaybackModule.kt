package com.example.onlineaudioplayer.di

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.onlineaudioplayer.feature.data.service.PlaybackService
import org.koin.dsl.module

val playbackModule = module(createdAtStart = false) {
    single {
        val context: Context = get()
        val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        MediaController.Builder(context, sessionToken).buildAsync()
    }
}