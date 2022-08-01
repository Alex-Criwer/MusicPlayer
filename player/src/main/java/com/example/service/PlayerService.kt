package com.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.music_player.PlayerInstance
import com.example.notification.PlayerNotificationManager
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.*


private const val TAG = "PlayerService"

class PlayerService: Service() {

    private val binder = LocalBinder()
    private val player = PlayerInstance(this)
    private val notificationManager = PlayerNotificationManager()

    private var coroutineScope = createCoroutineScope()

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
            coroutineScope = createCoroutineScope()
        }
    }

    fun play(mediaItem: MediaItem){
        player.play(mediaItem)
    }

    inner class LocalBinder: Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }

    override fun onCreate() {
        super.onCreate()
        val notification = notificationManager.createNotification(this, "AAAAAA")
        startForeground(PlayerNotificationManager.NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    private fun createCoroutineScope() = CoroutineScope(Job() + Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}