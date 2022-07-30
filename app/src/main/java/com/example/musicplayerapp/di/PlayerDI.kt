package com.example.musicplayerapp.di

import com.example.musicplayerapp.player.MediaPlayer
import org.koin.dsl.module

val playerModule = module {
    single {
        MediaPlayer()
    }
}