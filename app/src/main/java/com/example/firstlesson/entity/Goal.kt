package com.example.firstlesson.entity

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "date") var date: Date?,
    @ColumnInfo(name = "longitude") var longitude: Double?,
    @ColumnInfo(name = "latitude") var latitude: Double?
)