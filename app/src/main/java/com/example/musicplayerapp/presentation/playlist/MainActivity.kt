package com.example.musicplayerapp.presentation.playlist


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.storage.entity.Song
import com.example.musicplayerapp.presentation.playlist.helpers.SongClickListener
import com.example.musicplayerapp.presentation.playlist.rv.PlaylistAdapter
import com.example.musicplayerapp.presentation.playlist.viewmodel.PlaylistViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), SongClickListener {
    private val vm by viewModel<PlaylistViewModel>()
    private lateinit var songsAdapter: PlaylistAdapter
    private lateinit var songsRecyclerView: RecyclerView
    private lateinit var fab: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initRV()
        initSubscribe()

    }

    override fun onSongIconClick(song: Song) {
        TODO("Not yet implemented")
    }

    private fun initViews() {
        songsRecyclerView = findViewById(R.id.rv_playlist)
        fab = findViewById(R.id.fab)
    }

    private fun initRV() {
        songsAdapter = PlaylistAdapter(this@MainActivity)
        songsRecyclerView.adapter = songsAdapter
    }

    private fun initSubscribe() {
        vm.playlistLive.observe(this, Observer(songsAdapter::initData))
    }

}