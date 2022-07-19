package com.example.musicplayerapp.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicplayerapp.data.storage.dao.SongDao
import com.example.musicplayerapp.data.storage.entity.Song


@Database(entities = [Song::class], version = 1)
abstract class SongRoomDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        private val DATABASE_NAME = "Songs.db"
    }
}