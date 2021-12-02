package com.example.firstlesson.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.firstlesson.entity.Track
import com.example.firstlesson.entity.TracksRepository
import com.example.firstlesson.notification.MyNotification

class MusicService : Service() {

    lateinit var mediaPlayer: MediaPlayer
    private val tracks = TracksRepository.songList
    var currentTrackId: Int = 0

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "PREVIOUS" -> setSong(false)
            "RESUMEORPAUSE" -> pauseOrResume()
            "NEXT" -> setSong(true)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder = MusicBinder()

    private fun changeTrack(isNext: Boolean, track: Track): Track {
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

    fun setSong(foo: Boolean) {
        val x = changeTrack(foo, tracks[currentTrackId]).id
        stop()
        MyNotification(this).apply {
            build(x)
        }
        currentTrackId = x
        play(tracks[x])
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