package com.example.snoozeloo.domain.repository

import android.content.Intent
import com.example.snoozeloo.domain.entity.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun setAlarm(alarm: Alarm)

    suspend fun getAlarm(id: String): Alarm?

    suspend fun updateAlarm(alarm: Alarm)

    suspend fun deleteAlarm(id: String)

    suspend fun listenAlarms(): Flow<List<Alarm>>

    suspend fun canScheduleAlarm(): Boolean
}