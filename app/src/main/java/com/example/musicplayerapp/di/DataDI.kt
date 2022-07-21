package com.example.musicplayerapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.musicplayerapp.data.repository.SongRepositoryImpl
import com.example.musicplayerapp.data.storage.SongRoomDatabase
import com.example.musicplayerapp.domain.repository.SongRepository
import org.koin.dsl.module

val dataModule = module {
    single { createSongRoomDB(get())}
    single { SongRepositoryImpl(storage = get()) }
}

internal fun createSongRoomDB(context: Application): SongRoomDatabase {
    return Room.databaseBuilder(
        context,
        SongRoomDatabase::class.java,
        SongRoomDatabase.DATABASE_NAME)
        .allowMainThreadQueries()
        .build()
}