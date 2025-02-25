package com.example.snoozeloo.data

import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun setAlarm(alarm: Alarm)

    fun getAlarm(id: String): Alarm?

    fun updateAlarm(alarm: Alarm)

    fun deleteAlarm(id: String)

    fun listenAlarmsFlow(): Flow<List<Alarm>>
}