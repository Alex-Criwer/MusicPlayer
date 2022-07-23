package com.example.musicplayerapp.di

import com.example.musicplayerapp.presentation.playlist.viewmodel.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        PlaylistViewModel(addSongUseCase = get(),
                          deleteSongUseCase = get(),
                          getAllSongsUseCase = get())
    }
}