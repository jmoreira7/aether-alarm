package com.example.snoozeloo.data

import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class DefaultAlarmRepository(
    private val alarmScheduler: AlarmScheduler,
    private val alarmDao: AlarmDao
) : AlarmRepository {
    override suspend fun setAlarm(alarm: Alarm) {
        alarmScheduler.scheduleAlarm(alarm.id, alarm.triggerTime)
    }

    override suspend fun getAlarm(id: String): Alarm? {
        // ToDo
        return null
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        // ToDo
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        alarmScheduler.cancelAlarm(alarm.id)
    }

    override suspend fun listenAlarms(): Flow<List<Alarm>> {
        // ToDo
        return emptyFlow()
    }

    override suspend fun canScheduleAlarm(): Boolean = alarmScheduler.canScheduleExactAlarms()
}