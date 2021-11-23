package com.example.firstlesson.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.firstlesson.R

class AlarmChannelForService(private var context: Context) {

    private val manager by lazy {
        ContextCompat.getSystemService(context, NotificationManager::class.java)
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
            val channel = NotificationChannel(
                "ALARMFORSERVICE",
                "Уведомление будильника",
                NotificationManager.IMPORTANCE_LOW)
            manager?.createNotificationChannel(channel)
        }
    }
}