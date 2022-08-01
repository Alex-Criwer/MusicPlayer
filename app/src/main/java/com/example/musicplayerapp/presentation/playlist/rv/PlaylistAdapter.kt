package com.example.musicplayerapp.presentation.playlist.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.presentation.playlist.helpers.SongClickListener

class PlaylistAdapter(val songClickListener: SongClickListener): RecyclerView.Adapter<PlaylistAdapter.PlayListViewHolder>() {
    private val _songs: MutableList<Song> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun initData(listOfSongs: List<Song>) {
        _songs.clear()
        _songs.addAll(listOfSongs)
        notifyDataSetChanged()
    }


    inner class PlayListViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val songIcon = itemView.findViewById<AppCompatImageView>(R.id.music_item_picture_iv)
        val songName = itemView.findViewById<TextView>(R.id.song_name_tv)

        fun onBind(song: Song) {
            songName.text = song.title
            songName.isSelected = true
//            Glide.with(itemView.context)
//                 .load(song.icon)
//                 .into(songIcon)

            itemView.setOnClickListener {
                songClickListener.onSongIconClick(song)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return PlayListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.onBind(_songs[position])
    }

    override fun getItemCount(): Int = _songs.size
}