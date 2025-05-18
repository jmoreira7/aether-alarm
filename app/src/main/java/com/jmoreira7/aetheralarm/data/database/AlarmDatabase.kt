package com.jmoreira7.aetheralarm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jmoreira7.aetheralarm.domain.entity.Alarm

@Database(entities = [Alarm::class], version = 2, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}