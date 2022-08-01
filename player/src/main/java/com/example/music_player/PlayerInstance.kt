package com.example.music_player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class PlayerInstance(val context: Context): Player.Listener {
    private var player: ExoPlayer? = null

    fun play(mediaItem: MediaItem) {
        player?.let {
            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
        }
    }

    init {
        player = player ?: ExoPlayer.Builder(context.applicationContext).build()
        player?.addListener(this)
    }
}