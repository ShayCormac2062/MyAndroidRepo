package com.example.firstlesson.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle as NotificationCompatMediaStyle
import com.example.firstlesson.R
import com.example.firstlesson.entity.TracksRepository
import com.example.firstlesson.service.MusicService
import com.example.firstlesson.ui.MainActivity
import com.example.firstlesson.ui.fragment.OneSongFragment


class MyNotification(private val context: Context) {

    private var notificationManager: NotificationManager? = null
    private lateinit var nBuilder: NotificationCompat.Builder

    init {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                notificationManager?.getNotificationChannel("CHANNEL") ?: NotificationChannel(
                    "CHANNEL",
                    "Уведомление плеера",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Здесь могла быть ваша реклама, но мне никто не заплатил ._."
                }
            notificationManager?.createNotificationChannel(channel)
        }

        val previousIntent = Intent(context, MusicService::class.java).apply {
            action = "PREVIOUS"
        }
        val resumeIntent = Intent(context, MusicService::class.java).apply {
            action = "RESUMEORPAUSE"
        }
        val nextIntent = Intent(context, MusicService::class.java).apply {
            action = "NEXT"
        }
        val previousPendingIntent = PendingIntent.getService(context, 0, previousIntent, 0)
        val resumePendingIntent = PendingIntent.getService(context, 1, resumeIntent, 0)
        val nextPendingIntent = PendingIntent.getService(context, 2, nextIntent, 0)

        nBuilder = NotificationCompat.Builder(context, "CHANNEL")
            .setSmallIcon(R.drawable.resume)
            .setAutoCancel(true)
            .addAction(R.drawable.previous, "Previous", previousPendingIntent)
            .addAction(R.drawable.abstract_icon, "PauseOrResume", resumePendingIntent)
            .addAction(R.drawable.next, "Next", nextPendingIntent)
    }

    fun build(trackId: Int) {
        val notificationIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java).also {
                it.putExtra("TRACK", trackId)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val track = TracksRepository.songList[trackId]
        val cover = BitmapFactory.decodeResource(context.resources, track.cover)
        val builder = nBuilder
            .setContentTitle(track.name ?: "Название не указано")
            .setContentIntent(notificationIntent)
            .setContentText(track.author ?: "Автор неизвестен")
            .setStyle(NotificationCompatMediaStyle())
            .setLargeIcon(cover)
            .setColor(Color.BLACK)
        notificationManager?.notify(1, builder.build())
    }

}