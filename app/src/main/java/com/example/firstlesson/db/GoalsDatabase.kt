package com.example.firstlesson.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.firstlesson.db.dao.GoalsDao
import com.example.firstlesson.entity.Goal

@Database(
    entities = [Goal::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(GoalsDao.DateConvertors::class)
abstract class GoalsDatabase : RoomDatabase() {
    abstract fun goalsDao(): GoalsDao
}