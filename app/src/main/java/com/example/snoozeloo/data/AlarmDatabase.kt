package com.example.snoozeloo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.snoozeloo.domain.entity.Alarm

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}