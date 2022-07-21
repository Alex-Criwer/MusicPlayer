package com.example.musicplayerapp.domain.repository

import com.example.musicplayerapp.data.storage.entity.Song

interface SongRepository {
    fun addSong(song: Song): Long
    fun deleteSong(song: Song)
    fun getAllSongs(): List<Song>
}