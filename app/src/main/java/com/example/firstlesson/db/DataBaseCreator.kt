package com.example.firstlesson.db

import android.app.Activity
import androidx.room.Room

class DataBaseCreator {

    fun createDBInstance(activity: Activity): GoalsDatabase { // TaskDao AchievementDao
        return Room.databaseBuilder(activity, GoalsDatabase::class.java, "dao")
            .allowMainThreadQueries()
            .build()
    }

    fun initDB(activity: Activity): GoalsDatabase {
        val db = createDBInstance(activity)
        val taskDao = db.goalsDao()
        return db
    }
}