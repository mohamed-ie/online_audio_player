package com.example.onlineaudioplayer.feature.data.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.media3.common.Player
import androidx.media3.common.util.Util
import androidx.media3.session.CommandButton
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaNotification.ActionFactory
import androidx.media3.session.MediaSession
import com.example.onlineaudioplayer.R
import com.example.onlineaudioplayer.feature.presentation.MainActivity
import com.google.common.collect.ImmutableList
import androidx.media.app.NotificationCompat as MediaNotificationCompat

@SuppressLint("UnsafeOptInUsageError")
class PlaybackNotificationProvider(private val context: Context) : MediaNotification.Provider {

    override fun createNotification(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: ActionFactory,
        onNotificationChangedCallback: MediaNotification.Provider.Callback
    ): MediaNotification {
        return MediaNotification(
            DefaultMediaNotificationProvider.DEFAULT_NOTIFICATION_ID,
            buildNotification(
                mediaSession = mediaSession,
                actionFactory = actionFactory
            )
        )
    }

    override fun handleCustomCommand(
        session: MediaSession,
        action: String,
        extras: Bundle
    ): Boolean {
        return true
    }

    private fun buildNotification(
        mediaSession: MediaSession,
        actionFactory: ActionFactory
    ): Notification {
        ensureNotificationChannel()
        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = mediaSession.player.currentMediaItem?.mediaMetadata?.title
        val description = mediaSession.player.currentMediaItem?.mediaMetadata?.description
        val isPlaying = mediaSession.player.isPlaying
        val playPauseAction = NotificationCompat.Action(
            if (isPlaying) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24,
            if (isPlaying) context.getString(R.string.pause) else context.getString(R.string.play),
            actionFactory.createMediaActionPendingIntent(
                mediaSession,
                Player.COMMAND_PLAY_PAUSE.toLong()
            )
        )
        val stopAction = NotificationCompat.Action(
            R.drawable.round_stop_24,
            context.getString(R.string.stop),
            actionFactory.createMediaActionPendingIntent(mediaSession, Player.COMMAND_STOP.toLong())
        )

        return NotificationCompat
            .Builder(context, DefaultMediaNotificationProvider.DEFAULT_CHANNEL_ID)
            .setContentIntent(notifyPendingIntent)
            .setSmallIcon(R.drawable.twotone_radio_24)
            .setUsesChronometer(isPlaying)
            .setContentTitle(title)
            .setContentText(description)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(playPauseAction)
            .addAction(stopAction)
            .setPriority(NotificationManager.IMPORTANCE_LOW)
            .setColorized(true)
            .setStyle(
                MediaNotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1)
                    .setMediaSession(mediaSession.sessionCompatToken)
            )
            .build()
    }

    private fun ensureNotificationChannel() {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val channelId = DefaultMediaNotificationProvider.DEFAULT_CHANNEL_ID
        val channelName = DefaultMediaNotificationProvider.DEFAULT_CHANNEL_ID

        if (Util.SDK_INT < 26 || notificationManager.getNotificationChannel(channelId) != null)
            return

        notificationManager.createNotificationChannel(
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
        )
    }

}