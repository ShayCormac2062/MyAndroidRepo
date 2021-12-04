package com.example.firstlesson.activity

import android.app.*
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstlesson.MyService
import com.example.firstlesson.R
import com.example.firstlesson.alarm.AlarmChannel
import com.example.firstlesson.alarm.AlarmChannelForService
import com.example.firstlesson.data.SaveData
import com.example.firstlesson.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var datePicker: MaterialTimePicker
    private var pendingIntent: PendingIntent? = null
    private var calendar: Calendar? = null
    private var alarmManager: AlarmManager? = null
    private var alarmChannel: AlarmChannel? = null
    private var alarmChannelForService: AlarmChannelForService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmChannel = AlarmChannel(this).also {
            it.createNotificationChannel()
        }
        alarmChannelForService = AlarmChannelForService(this).also {
            it.createNotificationChannel()
        }
        begin()
    }

    private fun begin() {
        with(binding) {
            selectTimeBtn.setOnClickListener {
                showTimePicker()
            }
            runTimeBtn.setOnClickListener {
                if (calendar != null) {
                    alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                    pendingIntent = SaveData(applicationContext).setAlarm(binding.root, calendar, alarmManager)
                } else {
                    Snackbar.make(binding.root, "У вас нет назначенного времени", 1500).show()
                }
            }
            deleteAlarmBtn.setOnClickListener {
                if (calendar != null) {
                    AlertDialog.Builder(this@MainActivity).apply {
                        setTitle(R.string.delete_time)
                        setMessage("Вы точно хотите отключить будильник?")
                        setPositiveButton("Да") { dialog, _ ->
                            alarmManager?.cancel(pendingIntent)
                            alarmManager = null
                            binding.selectedTime.text = "Время не назначено"
                            //Здесь надо исправить, если перестанет работать
                            context.stopService(Intent(context, MyService::class.java))
                            calendar = null
                            dialog.dismiss()
                        }
                        setNegativeButton("Нет") { dialog, _ ->
                            dialog.dismiss()
                        }
                    }.show()
                } else {
                    Snackbar.make(binding.root, "У вас нет назначенного времени", 1500).show()
                }
            }
        }
    }

    private fun showTimePicker() {
        datePicker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Выберите время для будильника")
            .build()
        with(datePicker) {
            show(supportFragmentManager, "ALARM")
            addOnPositiveButtonClickListener {
                calendar = Calendar.getInstance().also {
                    it[Calendar.HOUR_OF_DAY] = datePicker.hour
                    it[Calendar.MINUTE] = datePicker.minute
                    it[Calendar.SECOND] = 0
                    it[Calendar.MILLISECOND] = 0
                }
                binding.selectedTime.text = getStringForTitle()
            }
        }
    }

    private fun getStringForTitle(): String? {
        calendar?.let {
            val hours = when (it[Calendar.HOUR_OF_DAY] < 10) {
                true -> "0${it[Calendar.HOUR_OF_DAY]}"
                else -> "${it[Calendar.HOUR_OF_DAY]}"
            }
            val minutes = when (it[Calendar.MINUTE] < 10) {
                true -> "0${it[Calendar.MINUTE]}"
                else -> "${it[Calendar.MINUTE]}"
            }
            return "Будильник прозвенит в $hours:$minutes"
        }
        return null
    }

}
