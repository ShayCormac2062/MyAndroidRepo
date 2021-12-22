package com.example.firstlesson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstlesson.databinding.ActivitySecondBinding
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startWork()
    }

    private fun startWork() {
        binding.photo.setImageURI(intent?.data)
        binding.path.text = intent?.data.toString()
        Snackbar.make(binding.root, "Было получено фото из галереи", 2000).show()
        binding.returnBtn.setOnClickListener {
            startActivity(Intent(this@SecondActivity, MainActivity::class.java))
        }
    }

}