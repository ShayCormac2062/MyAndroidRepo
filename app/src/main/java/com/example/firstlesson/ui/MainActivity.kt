package com.example.firstlesson.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstlesson.R
import com.example.firstlesson.databinding.ActivityMainBinding
import com.example.firstlesson.ui.fragment.AllSongsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
    }

    private fun start() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, AllSongsFragment())
            .commit()
    }

}
