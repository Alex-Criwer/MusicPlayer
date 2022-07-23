package com.example.musicplayerapp.presentation.playlist


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
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

        fab.setOnClickListener {
            if (!checkPermission()) {
                requestPermission()
                return@setOnClickListener
            }
            getMusicList()
        }
    }

    private fun initRV() {
        songsAdapter = PlaylistAdapter(this@MainActivity)
        songsRecyclerView.adapter = songsAdapter
        songsRecyclerView.layoutManager = GridLayoutManager(this,3 )
    }

    private fun initSubscribe() {
        vm.playlistLive.observe(this, Observer(songsAdapter::initData))
    }


    private fun checkPermission(): Boolean {
        val resultCode = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return resultCode == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_CODE)
    }


    @SuppressLint("Range")
    private fun getMusicList() {
        var numberOfSongs = 0

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION
        )

        val cursor = contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,null, null, null)

        while (cursor?.moveToNext() == true) {
            val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))

            val song = Song(id = id, title = title, duration = duration, path = path)
            vm.addSong(song)
            numberOfSongs += 1
        }

        if (numberOfSongs == 0) {
            Toast.makeText(this, "No music found", Toast.LENGTH_SHORT).show()
        }

        cursor?.close()
    }

    companion object {
        const val REQUEST_PERMISSION_CODE = 111
    }
}