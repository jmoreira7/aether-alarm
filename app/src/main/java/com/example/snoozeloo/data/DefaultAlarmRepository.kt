package com.example.snoozeloo.data

import android.content.Intent
import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow

class DefaultAlarmRepository(
    private val alarmScheduler: AlarmScheduler
) : AlarmRepository {
    override suspend fun setAlarm(alarm: Alarm) {
        alarmScheduler.scheduleAlarm()
    }

    override suspend fun getAlarm(id: String): Alarm? {
        // ToDo
        return null
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        // ToDo
    }

    override suspend fun deleteAlarm(id: String) {
        // ToDo
    }

    override fun listenAlarms(): Flow<List<Alarm>> {
        // ToDo
        return emptyFlow()
    }

    override fun canScheduleAlarm(): Boolean = alarmScheduler.canScheduleExactAlarms()
}