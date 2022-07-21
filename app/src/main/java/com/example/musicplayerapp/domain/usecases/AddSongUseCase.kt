package com.example.musicplayerapp.domain.usecases

import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.domain.repository.SongRepository

class AddSongUseCase(val songRepository: SongRepository) {
    fun execute(song: Song): Long {
        return songRepository.addSong(song)
    }
}