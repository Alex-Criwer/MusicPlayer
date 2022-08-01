package com.example.musicplayerapp.data.storage.entity

import android.os.Parcelable
import androidx.core.net.toUri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.util.MimeTypes
import kotlinx.android.parcel.Parcelize
import java.io.File

@Entity(tableName = "songs")
@Parcelize
data class Song (
    @PrimaryKey
    val id: String,
    val title: String?,
    val duration: String?,
    val path:String?
): Parcelable {
    companion object{
        fun Song.createMediaItem() = MediaItem.Builder().setMediaId(this.id)
            .setUri(path?.let { File(it).toUri() })
            .setMimeType(MimeTypes.AUDIO_MPEG)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setAlbumTitle(title)
                    .build()
            ).build()
    }
}
