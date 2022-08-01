package com.example.musicplayerapp.presentation.playlist


import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
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
import com.example.musicplayerapp.data.storage.entity.Song.Companion.createMediaItem
import com.example.musicplayerapp.presentation.player.PlayerActivity
import com.example.musicplayerapp.presentation.playlist.helpers.SongClickListener
import com.example.musicplayerapp.presentation.playlist.rv.PlaylistAdapter
import com.example.musicplayerapp.presentation.playlist.viewmodel.PlaylistViewModel
import com.example.service.PlayerService
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity(), SongClickListener {
    private val vm by viewModel<PlaylistViewModel>()
    private lateinit var songsAdapter: PlaylistAdapter
    private lateinit var songsRecyclerView: RecyclerView
    private lateinit var fab: ExtendedFloatingActionButton

    private var isBound = false
    private var playerService: PlayerService?= null

    private val startConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            isBound = true
            playerService = (binder as PlayerService.LocalBinder).getService()
            playerService!!.startForeground()
            Timber.d("player onServiceConnected $playerService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Timber.d("player onServiceDisconnected")
            isBound = false
            playerService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initRV()
        initSubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(applicationContext, PlayerService::class.java))
    }

    override fun onStart() {
        super.onStart()
        vm.getAllSongsFromDB()
        onBindService()
    }

    override fun onStop() {
        super.onStop()
        onUnbindService()
    }

    override fun onSongIconClick(song: Song) {
        Timber.d("player s state is $playerService")
        playerService?.play(song.createMediaItem())
    }

    private fun initViews() {
        songsRecyclerView = findViewById(R.id.rv_playlist)
        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            if (!checkPermission()) {
                requestPermission()
                return@setOnClickListener
            }
            openMusicList()
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
        if (checkPermission()) {
            openMusicList()
        }
    }


    @SuppressLint("Range")
    private fun addMusic(uri: Uri) {
        var numberOfSongs = 0

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION
        )

        val cursor = contentResolver?.query(
            uri,
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

    private fun openMusicList() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_AUDIO_KEY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_AUDIO_KEY) {
            data?.data?.let {
                addMusic(it)
            }
        }
    }


    private fun onBindService() {
        if (!isBound) {
            val res = bindService(
                Intent(this, PlayerService::class.java),
                startConnection,
                BIND_AUTO_CREATE)
            //isBound = true
            Timber.d("player onBind service in activity $res")
        }
    }

    private fun onUnbindService() {
        if (isBound) {
            unbindService(startConnection)
            isBound = false
            Timber.d("player onUnbind service")
        }
    }

    companion object {
        const val REQUEST_PERMISSION_CODE = 111
        const val PICK_AUDIO_KEY = 112
    }
}