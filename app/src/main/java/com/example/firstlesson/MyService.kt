package com.example.firstlesson

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyService : Service() {

    override fun onCreate() {
        startForeground(2, NotificationCompat.Builder(applicationContext, "ALARMFORSERVICE")
            .setSmallIcon(R.drawable.timer)
            .setContentTitle("Будильник запущен")
            .setContentText("Он зазвонит в указанное время)")
            .build())
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_REDELIVER_INTENT
    }

    override fun startForegroundService(service: Intent?): ComponentName? {
        return super.startForegroundService(service)
    }

    override fun onDestroy() {
        this.stopSelf()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? = null
}