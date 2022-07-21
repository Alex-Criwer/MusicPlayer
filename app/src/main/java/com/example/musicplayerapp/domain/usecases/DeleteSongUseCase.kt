package com.example.musicplayerapp.domain.usecases

import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.domain.repository.SongRepository

class DeleteSongUseCase(private val songRepository: SongRepository) {
    fun execute(song: Song) {
        songRepository.deleteSong(song)
    }
}