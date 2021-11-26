package com.example.firstlesson.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.annotation.MainThread
import androidx.fragment.app.FragmentManager
import com.example.firstlesson.R
import com.example.firstlesson.databinding.FragmentOneSongBinding
import com.example.firstlesson.entity.Track
import com.example.firstlesson.entity.TracksRepository
import com.example.firstlesson.notification.MyNotification
import com.example.firstlesson.ui.MainActivity
import com.example.firstlesson.ui.fragment.OneSongFragment

class MusicService : Service() {

    lateinit var mediaPlayer: MediaPlayer
    private lateinit var musicBinder: MusicBinder
    private val tracks = TracksRepository.songList
    var currentTrackId: Int = 0
    var parentFragmentManager: FragmentManager? = null

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        musicBinder = MusicBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "PREVIOUS" -> setSong(false)
            "RESUMEORPAUSE" -> pauseOrResume()
            "NEXT" -> setSong(true)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun setSong(foo: Boolean) {
        val x = changeTrack(foo, tracks[currentTrackId]).id
        stop()
        MyNotification(this).apply {
            build(x)
        }
        currentTrackId = x
        play(tracks[x])
        if (parentFragmentManager != null) {
            setScreen(tracks[x])
        }
    }

    fun setScreen(track: Track) {
        parentFragmentManager?.beginTransaction()?.replace(
            R.id.container,
            OneSongFragment(track)
        )?.commit()
    }

    override fun onBind(intent: Intent): IBinder = MusicBinder()

    fun changeTrack(isNext: Boolean, track: Track): Track {
        val index = tracks.indexOf(track)
        return when (isNext) {
            false -> {
                if (index == 0) tracks[tracks.size - 1]
                else tracks[index - 1]
            }
            else -> {
                if (index == tracks.size - 1) tracks[0]
                else tracks[index + 1]
            }
        }
    }

    fun play(track: Track) {
        mediaPlayer = MediaPlayer.create(
            this,
            track.music
        ).also {
            it.start()
        }
    }

    fun pauseOrResume(): Int {
        return if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            1
        } else {
            mediaPlayer.start()
            0
        }
    }

    fun stop() {
        with(mediaPlayer) {
            stop()
            release()
        }
    }
}