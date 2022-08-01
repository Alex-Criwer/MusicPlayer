package com.example.musicplayerapp.presentation.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.data.storage.entity.Song.Companion.createMediaItem
import com.example.service.PlayerService
import timber.log.Timber


class PlayerActivity : AppCompatActivity() {

    private var song: Song? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("player onCreate activity")
        setContentView(R.layout.activity_player)
    }


    companion object {
        fun start(context: Context, song: Song) {
            val intent = Intent(context, PlayerActivity::class.java).apply {
                putExtra(Song::class.java.name, song)
            }
            context.startActivity(intent)
        }
    }

}