package com.example.firstlesson.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import com.example.firstlesson.MyService
import com.example.firstlesson.alarm.AlarmReceiver
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SaveData(private var context: Context) {

    fun setAlarm(
        view: View,
        calendar: Calendar?,
        alarmManager: AlarmManager?,
    ): PendingIntent? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            calendar?.timeInMillis?.let {
                val i = Intent(context, AlarmReceiver::class.java).apply {
                    action = "com.tester.alarmManager"
                }
                val pendingIntent =
                    PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager?.setExact(AlarmManager.RTC_WAKEUP, it, pendingIntent)
                context.startForegroundService(Intent(context, MyService::class.java))
                Snackbar.make(view, "Ваш будильник зазвенит в указанное время", 2000).show()
                return pendingIntent
            }
        }
        return null
    }

}