package com.example.music_player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import timber.log.Timber
import java.lang.Exception

class PlayerInstance(val context: Context): Player.Listener {
    private var player: ExoPlayer? = null

    fun play(mediaItem: MediaItem) {
        Timber.d("player start playing, state is $player")
        try {
            player?.let {
                it.setMediaItem(mediaItem)
                it.prepare()
                it.play()
                it.volume = 1F
            }
        } catch (e: Exception) {
            Timber.d("player exception ${e.message}")
        }
    }

    private fun initializePlayer() {
        Timber.d("player initialize")
        if (player == null) {
            player = ExoPlayer.Builder(context.applicationContext).build()
            player?.addListener(this)
        }
    }

    init {
        initializePlayer()
    }

}