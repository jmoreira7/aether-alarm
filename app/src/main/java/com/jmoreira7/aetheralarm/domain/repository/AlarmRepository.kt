package com.jmoreira7.aetheralarm.domain.repository

import com.jmoreira7.aetheralarm.domain.entity.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun setAlarm(alarm: Alarm)

    suspend fun getAlarm(id: Int): Alarm?

    suspend fun updateAlarm(alarm: Alarm)

    suspend fun deleteAlarm(alarmId: Int)

    suspend fun listenAlarms(): Flow<List<Alarm>>

    suspend fun canScheduleAlarm(): Boolean
}