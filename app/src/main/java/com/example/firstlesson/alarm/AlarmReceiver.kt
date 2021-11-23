package com.example.firstlesson.alarm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firstlesson.MyService
import com.example.firstlesson.R
import com.example.firstlesson.activity.WakeUpActivity
import com.example.firstlesson.data.SaveData

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action == "com.tester.alarmManager") {
                val i = Intent(context, WakeUpActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)
                val notification = context?.let { cont ->
                    NotificationCompat.Builder(cont, "ALARM")
                        .setSmallIcon(R.drawable.clock)
                        .setContentTitle("Прозвенел будильник!")
                        .setContentText("Самая пора выключить его")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.ara_ara))
                        .setVibrate(longArrayOf(100L, 200L, 0L, 300L))
                        .setColor(Color.CYAN)
                }
                if (context != null) {
                    notification?.build()?.let { notif ->
                        NotificationManagerCompat.from(context).notify(1, notif)
                        context.stopService(Intent(context, MyService::class.java))
                    }
                }
            }
        }
    }
}