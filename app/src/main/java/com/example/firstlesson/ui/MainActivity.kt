package com.example.firstlesson.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.firstlesson.R
import com.example.firstlesson.databinding.ActivityMainBinding
import com.example.firstlesson.entity.TracksRepository
import com.example.firstlesson.ui.fragment.AllSongsFragment
import com.example.firstlesson.ui.fragment.OneSongFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
    }

    private fun start() {
        val trackId = intent.getIntExtra("TRACK", -1)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, if (trackId != -1) {
                OneSongFragment(TracksRepository.songList[trackId])
            } else AllSongsFragment())
            .commit()
    }

}
