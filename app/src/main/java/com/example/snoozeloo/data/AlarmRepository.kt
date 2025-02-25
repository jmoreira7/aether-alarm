package com.example.snoozeloo.data

import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun setAlarm(alarm: Alarm)

    suspend fun getAlarm(id: String): Alarm?

    suspend fun updateAlarm(alarm: Alarm)

    suspend fun deleteAlarm(id: String)

    fun listenAlarms(): Flow<List<Alarm>>

    fun listenAlarmEvents(): Flow<AlarmEvent>
}