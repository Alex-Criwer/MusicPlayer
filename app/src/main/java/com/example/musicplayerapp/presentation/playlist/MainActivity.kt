package com.example.musicplayerapp.presentation.playlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayerapp.R
import com.example.musicplayerapp.presentation.playlist.helpers.SongClickListener

class MainActivity : AppCompatActivity(), SongClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSongIconClick() {
        TODO("Not yet implemented")
    }
}