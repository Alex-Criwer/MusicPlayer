package com.example.musicplayerapp.data.storage.dao

import androidx.room.*
import com.example.musicplayerapp.data.storage.entity.Song

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSong(song: Song): Long

    @Query("DELETE FROM songs WHERE id = :id")
    fun deleteSongById(id: Long)

    @Delete
    fun deleteSong(song: Song)

    @Query("SELECT * FROM songs")
    fun getAllSongs(): MutableList<Song>
}