package com.example.firstlesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.firstlesson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        begin()
    }

    private fun begin() {
        with(binding) {
            btn1.setOnClickListener {
                val transaction = supportFragmentManager.beginTransaction()
                completeFragment(transaction, FirstFragment())
            }
            btn2.setOnClickListener {
                val transaction = supportFragmentManager.beginTransaction()
                completeFragment(transaction, SecondFragment())
            }
            btn3.setOnClickListener {
                val transaction = supportFragmentManager.beginTransaction()
                completeFragment(transaction, ThirdFragment())
            }
            btn4.setOnClickListener {
                val transaction = supportFragmentManager.beginTransaction()
                completeFragment(transaction, FourthFragment())
            }
        }
    }

    private fun completeFragment(transaction: FragmentTransaction, fragment: Fragment) {
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
