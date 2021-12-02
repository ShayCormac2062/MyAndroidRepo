package com.example.firstlesson.ui.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firstlesson.R
import com.example.firstlesson.databinding.FragmentOneSongBinding
import com.example.firstlesson.entity.Track
import com.example.firstlesson.entity.TracksRepository
import com.example.firstlesson.notification.MyNotification
import com.example.firstlesson.service.MusicService

class OneSongFragment(private var track: Track) : Fragment() {

    private lateinit var binding: FragmentOneSongBinding
    private val handler = Handler()
    private val updater: Runnable = Runnable {
        functionToUpdateSeekBar()
    }
    private var musicService: MusicService? = null
    private var connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            musicService = (service as? MusicService.MusicBinder)?.getService().also {
                context?.let { context ->
                    activity?.intent?.removeExtra("TRACK")
                    MyNotification(context).apply {
                        build(track.id)
                    }
                }
                if (track.id != it?.currentTrackId) {
                    if (it?.mediaPlayer?.isPlaying == true) it.stop()
                    it?.play(track)
                }
                it?.currentTrackId = track.id
            }
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneSongBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        musicService?.let {
            track = TracksRepository.songList[it.currentTrackId]
            drawScreen()
            updateSeekBar(it.mediaPlayer)
        }
        initService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            with(seekBar) {
                max = 100
                setOnTouchListener { _, _ ->
                    val playPosition =
                        (musicService?.mediaPlayer?.duration?.div(100))?.times(this.progress)
                    playPosition?.let {
                        musicService?.mediaPlayer?.seekTo(it)
                        trackTime.text = millisecondsToTimer(it)
                    }
                    false
                }
            }
            previousTrack.setOnClickListener {
                updateScreen(false)
            }
            nextTrack.setOnClickListener {
                updateScreen(true)
            }
            resumeTrack.setOnClickListener {
                when (musicService?.pauseOrResume()) {
                    1 -> {
                        handler.removeCallbacks(updater)
                        it.setBackgroundResource(R.drawable.resume)
                    }
                    else -> {
                        functionToUpdateSeekBar()
                        it.setBackgroundResource(R.drawable.pause)
                    }
                }
            }
            handler.postDelayed(updater, 250)
            drawScreen()
        }
    }

    private fun updateScreen(foo: Boolean) {
        handler.removeCallbacks(updater)
        musicService?.let {
            it.setSong(foo)
            track = TracksRepository.songList[it.currentTrackId]
        }
        drawScreen()
    }

    private fun functionToUpdateSeekBar() {
        musicService?.mediaPlayer?.let {
            binding.trackDuration.text = millisecondsToTimer(it.duration)
            updateSeekBar(it)
        }
    }

    private fun drawScreen() {
        with(binding) {
            trackName.text = track.name ?: "Название не указано"
            trackAuthor.text = track.author ?: "Автор неизвестен"
            if (musicService?.mediaPlayer?.isPlaying == false) {
                resumeTrack.setBackgroundResource(R.drawable.resume)
            } else {
                resumeTrack.setBackgroundResource(R.drawable.pause)
            }
            track.cover.let {
                trackCover.setImageResource(it)
            }
            functionToUpdateSeekBar()
        }
    }

    private fun updateSeekBar(player: MediaPlayer) {
        binding.seekBar.progress =
            ((player.currentPosition.toFloat()) / player.duration * 100).toInt()
        binding.trackTime.text = millisecondsToTimer(player.currentPosition)
        handler.postDelayed(updater, 250)
    }

    private fun millisecondsToTimer(time: Int): String {
        var result = ""
        val min: String
        val sec: String
        val hours = time / (1000 * 60 * 60)
        val minutes = time % (1000 * 60 * 60) / 60000
        val seconds = time % (1000 * 60 * 60) % 60000 / 1000
        if (hours > 0) {
            result = "$hours:"
        }
        min = if (minutes < 10) "0$minutes" else "$minutes"
        sec = if (seconds < 10) "0$seconds" else "$seconds"
        return "${result}$min:$sec"
    }

    private fun initService() = activity?.bindService(
        Intent(context, MusicService::class.java),
        connection,
        Context.BIND_AUTO_CREATE
    )

    override fun onDestroy() {
        super.onDestroy()
        musicService = null
        activity?.intent?.removeExtra("TRACK")
    }
}