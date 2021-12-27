package com.example.firstlesson.db

import android.app.Activity
import androidx.room.Room

class DataBaseCreator {

    fun initDB(activity: Activity) = Room.databaseBuilder(activity, GoalsDatabase::class.java, "dao")
        .build()

}