package com.example.firstlesson

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.example.firstlesson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var user: User = User("Башир", "Галеев", "Outcast ._.", "bashir.2002@mail.ru")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.also {
            with(user) {
                name = it.getString("NAME").toString()
                surname = it.getString("SURNAME").toString()
                email = it.getString("EMAIL").toString()
                status = it.getString("STATUS").toString()
            }
        }
        getUser()
        beginWork()
    }

    private fun beginWork() {
        with(binding) {
            change.setOnClickListener {
                changeInfo()
                confirm.setOnClickListener {
                    user.name = changeName.text.toString()
                    user.surname = changeSurname.text.toString()
                    user.status = statusChange.text.toString()
                    getUser()
                    defaultInfo()
                }
            }
            changeEmail.setOnClickListener {
                address.visibility = View.GONE
                addressChange.visibility = View.VISIBLE
                changeEmail.visibility = View.GONE
                confirmEmail.visibility = View.VISIBLE
                confirmEmail.setOnClickListener {
                    user.email = binding.addressChange.text.toString()
                    getUser()
                    address.visibility = View.VISIBLE
                    addressChange.visibility = View.GONE
                    changeEmail.visibility = View.VISIBLE
                    confirmEmail.visibility = View.GONE
                }
            }
            like.setOnClickListener { plusOne() }
            likeAmount.setOnClickListener { plusOne() }
        }
    }

    private fun plusOne() {
        binding.likeAmount.text =
            (Integer.parseInt(binding.likeAmount.text.toString()) + 1).toString()
    }

    @SuppressLint("SetTextI18n")
    private fun getUser() {
        with(binding) {
            nameOfUser.text = user.name
            surnameOfUser.text = user.surname
            status.text = user.status
            changeName.setText(user.name)
            changeSurname.setText(user.surname)
            statusChange.setText(user.status)
            address.text = user.email
            addressChange.setText(user.email)
            nameSurname.text = "${user.name} ${user.surname}"
        }
    }

    private fun defaultInfo() {
        with(binding) {
            change.visibility = View.VISIBLE
            nameOfUser.visibility = View.VISIBLE
            surnameOfUser.visibility = View.VISIBLE
            status.visibility = View.VISIBLE
            online.visibility = View.VISIBLE
            confirm.visibility = View.GONE
            changeName.visibility = View.GONE
            changeSurname.visibility = View.GONE
            statusChange.visibility = View.GONE
        }
    }

    private fun changeInfo() {
        with (binding) {
            change.visibility = View.GONE
            nameOfUser.visibility = View.GONE
            surnameOfUser.visibility = View.GONE
            status.visibility = View.GONE
            confirm.visibility = View.VISIBLE
            online.visibility = View.GONE
            changeName.visibility = View.VISIBLE
            changeSurname.visibility = View.VISIBLE
            statusChange.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.run {
            putString("NAME", user.name)
            putString("SURNAME", user.surname)
            putString("EMAIL", user.email)
            putString("STATUS", user.status)
        }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

}
