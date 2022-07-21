package com.example.musicplayerapp.domain.usecases

import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.domain.repository.SongRepository

class GetAllSongsUseCase(private val songRepository: SongRepository) {
    fun execute(): List<Song> {
        return songRepository.getAllSongs()
    }
}