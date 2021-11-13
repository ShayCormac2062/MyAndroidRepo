package com.example.firstlesson

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.firstlesson.databinding.ActivityMainBinding
import com.example.firstlesson.entity.Element

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    var elements: ArrayList<Element> = ArrayList()
    var pictures: ArrayList<Int> = arrayListOf(R.drawable.anime1,
        R.drawable.anime2, R.drawable.anime3, R.drawable.anime4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
    }

    @SuppressLint("ResourceType")
    private fun initNavController() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        setupActionBarWithNavController(navController, AppBarConfiguration(setOf(R.id.firstFragment, R.id.secondFragment, R.id.thirdFragment)))
        binding.bottomMenu.setupWithNavController(navController)
    }


}
