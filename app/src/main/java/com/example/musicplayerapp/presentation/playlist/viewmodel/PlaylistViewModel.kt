package com.example.musicplayerapp.presentation.playlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.domain.usecases.AddSongUseCase
import com.example.musicplayerapp.domain.usecases.DeleteSongUseCase
import com.example.musicplayerapp.domain.usecases.GetAllSongsUseCase

class PlaylistViewModel(
    private val addSongUseCase: AddSongUseCase,
    private val deleteSongUseCase: DeleteSongUseCase,
    private val getAllSongsUseCase: GetAllSongsUseCase
): ViewModel() {

    private val playlistLiveMutable = MutableLiveData<List<Song>>()
    val playlistLive: LiveData<List<Song>> = playlistLiveMutable

    fun addSong(song: Song) {
        val indexInTable = addSongUseCase.execute(song)
        playlistLiveMutable.value = getAllSongsUseCase.execute()
    }

    fun deleteSong(song: Song) {
        deleteSongUseCase.execute(song)
        playlistLiveMutable.value = getAllSongsUseCase.execute()
    }
}