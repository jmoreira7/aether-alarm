package com.example.snoozeloo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class DefaultAlarmRepository : AlarmRepository {
    override fun setAlarm(alarm: Alarm) {
        // ToDo
    }

    override fun getAlarm(id: String): Alarm? {
        // ToDo
        return null
    }

    override fun updateAlarm(alarm: Alarm) {
        // ToDo
    }

    override fun deleteAlarm(id: String) {
        // ToDo
    }

    override fun listenAlarmsFlow(): Flow<List<Alarm>> {
        // ToDo
        return emptyFlow()
    }
}