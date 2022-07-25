package com.example.musicplayerapp.presentation.playlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.domain.usecases.AddSongUseCase
import com.example.musicplayerapp.domain.usecases.DeleteSongUseCase
import com.example.musicplayerapp.domain.usecases.GetAllSongsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class PlaylistViewModel(
    private val addSongUseCase: AddSongUseCase,
    private val deleteSongUseCase: DeleteSongUseCase,
    private val getAllSongsUseCase: GetAllSongsUseCase
): ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, error ->
        Timber.d("timber error in PlaylistViewModel is ${error.message}")
    }

    private val playlistLiveMutable = MutableLiveData<List<Song>>()
    val playlistLive: LiveData<List<Song>> = playlistLiveMutable

    fun addSong(song: Song) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val indexInTable = addSongUseCase.execute(song)
                playlistLiveMutable.postValue(getAllSongsUseCase.execute())
            }
        }
    }

    fun deleteSong(song: Song) {
        deleteSongUseCase.execute(song)
        playlistLiveMutable.value = getAllSongsUseCase.execute()
    }

    fun getAllSongsFromDB() {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                playlistLiveMutable.postValue(getAllSongsUseCase.execute())
            }
        }
    }
}