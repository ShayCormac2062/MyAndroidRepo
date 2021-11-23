package com.example.firstlesson.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import com.example.firstlesson.R

class AlarmChannel(private var context: Context) {

    private val manager by lazy {
        getSystemService(context, NotificationManager::class.java)
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
            val channel = NotificationChannel(
                "ALARM",
                "Уведомление будильника",
                NotificationManager.IMPORTANCE_HIGH)
            with(channel) {
                vibrationPattern = longArrayOf(100L, 200L, 0L, 300L)
                enableVibration(true)
                setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.ara_ara), audioAttributes)
                description = "Не знаю, что сюда написать, но пусть будет так)"
            }
            manager?.createNotificationChannel(channel)
        }
    }
}