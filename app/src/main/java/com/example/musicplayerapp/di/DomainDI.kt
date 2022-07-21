package com.example.musicplayerapp.di

import com.example.musicplayerapp.domain.usecases.AddSongUseCase
import com.example.musicplayerapp.domain.usecases.DeleteSongUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        AddSongUseCase(songRepository = get())
    }

    factory {
        DeleteSongUseCase(songRepository = get())
    }
}