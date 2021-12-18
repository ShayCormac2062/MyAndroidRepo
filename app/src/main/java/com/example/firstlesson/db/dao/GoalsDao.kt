package com.example.firstlesson.db.dao

import androidx.room.*
import com.example.firstlesson.entity.Goal
import java.util.*

@Dao
interface GoalsDao {

    class DateConvertors {

        @TypeConverter
        fun toDate(dateLong: Long?): Date? = if (dateLong == null) null else Date(dateLong)

        @TypeConverter
        fun fromDate(date: Date?): Long? = date?.time
    }

    @Query("SELECT * FROM goals")
    fun getAll(): List<Goal>

    @Insert
    fun add(goal: Goal)

    @Query("UPDATE goals SET title=:title WHERE (id=:id)")
    fun updateTitle(id: Int, title: String?)

    @Query("UPDATE goals SET description=:description WHERE (id=:id)")
    fun updateDescription(id: Int, description: String?)
    
    @Query("UPDATE goals SET date=:date WHERE (id=:id)")
    fun updateDate(id: Int, date: Date?)

    @Query("UPDATE goals SET latitude=:latitude WHERE (id=:id)")
    fun updateLatitude(id: Int, latitude: Double?)

    @Query("UPDATE goals SET longitude=:longitude WHERE (id=:id)")
    fun updateLongitude(id: Int, longitude: Double?)

    @Query("DELETE FROM goals WHERE id=:goal")
    fun deleteGoal(goal: Int)

    @Query("DELETE FROM goals")
    fun deleteAll()
}