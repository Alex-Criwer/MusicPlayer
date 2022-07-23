package com.example.musicplayerapp.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song (
    @PrimaryKey
    val id: String,
    val title: String?,
    val duration: String?,
    val path:String?
)
