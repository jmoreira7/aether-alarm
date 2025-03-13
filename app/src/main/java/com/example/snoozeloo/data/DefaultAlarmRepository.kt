package com.example.snoozeloo.data

import android.content.Intent
import com.example.snoozeloo.data.source.platform.AlarmScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow

class DefaultAlarmRepository(
    private val alarmScheduler: AlarmScheduler
) : AlarmRepository {
    private val _alarmEvents = MutableSharedFlow<AlarmEvent>(replay = 0)
    private val alarmEvents: Flow<AlarmEvent> = _alarmEvents

    override suspend fun setAlarm(alarm: Alarm, intent: Intent) {
        alarmScheduler.scheduleAlarm(intent)
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

    override fun listenAlarmEvents(): Flow<AlarmEvent> = alarmEvents

    override fun canScheduleAlarm(): Boolean = alarmScheduler.canScheduleExactAlarms()

    suspend fun triggerAlarm() {
        _alarmEvents.emit(AlarmEvent.Triggered)
    }
}