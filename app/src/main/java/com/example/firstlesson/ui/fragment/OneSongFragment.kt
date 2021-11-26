package com.example.firstlesson.ui.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firstlesson.R
import com.example.firstlesson.databinding.FragmentOneSongBinding
import com.example.firstlesson.entity.Track
import com.example.firstlesson.entity.TracksRepository
import com.example.firstlesson.notification.MyNotification
import com.example.firstlesson.service.MusicService

class OneSongFragment(private var track: Track) : Fragment() {

    private lateinit var binding: FragmentOneSongBinding
    private var musicService: MusicService? = null
    private var connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            musicService = (service as? MusicService.MusicBinder)?.getService().also {
                if (it?.mediaPlayer?.isPlaying == true) it.stop()
                it?.currentTrackId = track.id
                it?.parentFragmentManager = parentFragmentManager
                context?.let { context ->
                    MyNotification(context).apply {
                        build(track.id)
                    }
                }
                it?.play(track)
            }
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            onStop()
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
            it.parentFragmentManager = parentFragmentManager
            track = TracksRepository.songList[it.currentTrackId]
            drawScreen()
        }
        initService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            drawScreen()
            previousTrack.setOnClickListener {
                musicService?.setSong(false)
            }
            nextTrack.setOnClickListener {
                musicService?.setSong(true)
            }
            resumeTrack.setOnClickListener {
                when(musicService?.pauseOrResume()) {
                    1 -> it.setBackgroundResource(R.drawable.resume)
                    else -> it.setBackgroundResource(R.drawable.pause)
                }
            }
        }
    }

    private fun drawScreen() {
        with(binding) {
            trackName.text = track.name ?: "Название не указано"
            trackAuthor.text = track.author ?: "Автор неизвестен"
            if (musicService?.mediaPlayer?.isPlaying == false) {
                resumeTrack.setBackgroundResource(R.drawable.resume)
            } else resumeTrack.setBackgroundResource(R.drawable.pause)
            track.cover.let { trackCover.setImageResource(it) }
        }
    }

    private fun initService() = activity?.bindService(
        Intent(context, MusicService::class.java),
        connection,
        Context.BIND_AUTO_CREATE
    )

    override fun onPause() {
        musicService?.parentFragmentManager = null
        super.onPause()
    }

    override fun onStop() {
        musicService?.parentFragmentManager = null
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicService = null
    }

}