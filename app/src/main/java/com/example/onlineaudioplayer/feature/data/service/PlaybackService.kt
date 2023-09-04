package com.example.onlineaudioplayer.feature.data.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

@SuppressLint("UnsafeOptInUsageError")
class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    init {
        setMediaNotificationProvider(PlaybackNotificationProvider(this))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
    }


    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
            getSystemService(NotificationManager::class.java).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    deleteNotificationChannel(DefaultMediaNotificationProvider.DEFAULT_CHANNEL_ID)
                cancel(DefaultMediaNotificationProvider.DEFAULT_NOTIFICATION_ID)
            }
        }
        super.onDestroy()
    }

    override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
        super.onUpdateNotification(session, true)
    }

}