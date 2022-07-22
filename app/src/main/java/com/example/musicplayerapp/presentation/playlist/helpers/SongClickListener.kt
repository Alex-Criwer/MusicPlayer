package com.example.musicplayerapp.presentation.playlist.helpers

import com.example.musicplayerapp.data.storage.entity.Song

interface SongClickListener {
    fun onSongIconClick(song: Song)
}