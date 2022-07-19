package com.example.musicplayerapp.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song (
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val singer: String,
    val icon:String
)
