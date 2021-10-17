package com.example.firstlesson

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firstlesson.databinding.FragmentFirstBinding
import com.example.firstlesson.databinding.FragmentThirdBinding
import com.google.android.material.snackbar.Snackbar

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Snackbar.make(view, "Был запущен третий фрагмент", 1500).show()
        val videoPlayer = binding.videoPlayer
        videoPlayer.setVideoURI(Uri.parse("android.resource://" + context?.packageName + "/" + R.raw.tyan))
        videoPlayer.start()
        startWork()
    }

    private fun startWork() {
        with(binding) {
            startBtn.setOnClickListener {
                startBtn.visibility = View.GONE
                stopBtn.visibility = View.VISIBLE
                videoPlayer.start()
            }
            stopBtn.setOnClickListener {
                stopBtn.visibility = View.GONE
                startBtn.visibility = View.VISIBLE
                videoPlayer.stopPlayback()
                videoPlayer.resume()
            }
        }
    }
}