package com.example.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.musicplayerapp.R

class PlayerNotificationManager {

    fun createNotification(
        context: Context,
        title: String,
        pendingIntent: PendingIntent? = null): Notification {

        val notificationBuilder =  NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.music_note)
            .setContentTitle(title)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(createChannel(context))

        return notificationBuilder.build()
    }


    private fun createChannel(context: Context): NotificationChannel {
        return NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_DEFAULT)
    }

    companion object {
        private const val CHANNEL_ID = "channelId"
        const val NOTIFICATION_ID = 1
    }
}