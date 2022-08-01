package com.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.music_player.PlayerInstance
import com.example.notification.PlayerNotificationManager
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.*
import timber.log.Timber


private const val TAG = "PlayerService"

class PlayerService: Service() {

    private val binder = LocalBinder()
    private var player: PlayerInstance? = null
    private val notificationManager = PlayerNotificationManager()

    private var coroutineScope = createCoroutineScope()

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
            coroutineScope = createCoroutineScope()
        }
    }

    fun play(mediaItem: MediaItem){
        notificationManager.updateNotification(this, mediaItem.mediaMetadata.albumTitle.toString())
        Timber.d("player in service play")
        player?.play(mediaItem)
    }

    inner class LocalBinder: Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }

    fun startForeground() {
        ContextCompat.startForegroundService(
            applicationContext,
            Intent(this, PlayerService::class.java)
        )
    }

    override fun onCreate() {
        super.onCreate()
        player = PlayerInstance(this)
        val notification = notificationManager.createNotification(this, "")
        Timber.d("player onCreate service")
        startForeground(PlayerNotificationManager.NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }

    private fun createCoroutineScope() = CoroutineScope(Job() + Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("player onBind service")
        return binder
    }
}