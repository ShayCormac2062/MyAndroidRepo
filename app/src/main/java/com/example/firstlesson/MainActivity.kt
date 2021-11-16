package com.example.firstlesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstlesson.databinding.ActivityMainBinding
import com.example.firstlesson.fragment.CharacterListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        begin()
    }

    private fun begin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CharacterListFragment())
            .addToBackStack(null)
            .commit()
    }

}
