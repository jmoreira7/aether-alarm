package com.example.snoozeloo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.snoozeloo.domain.entity.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert
    suspend fun insertAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)

    @Query("DELETE FROM alarms WHERE id == :id")
    suspend fun deleteAlarm(id: Int)

    @Query("SELECT * FROM alarms WHERE id = :id")
    suspend fun getAlarmById(id: Int): Alarm?

    @Query("SELECT * FROM alarms")
    fun getAllAlarms(): Flow<List<Alarm>>
}