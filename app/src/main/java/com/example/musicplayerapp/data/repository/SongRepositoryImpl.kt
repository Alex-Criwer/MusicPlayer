package com.example.musicplayerapp.data.repository

import com.example.musicplayerapp.data.storage.SongRoomDatabase
import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.domain.repository.SongRepository

class SongRepositoryImpl(val storage: SongRoomDatabase): SongRepository {
    override fun addSong(song: Song): Long {
        return storage.songDao().addSong(song)
    }

    override fun deleteSong(song: Song) {
        storage.songDao().deleteSong(song)
    }
}