package com.example.snoozeloo.data

import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultAlarmRepository(
    private val alarmScheduler: AlarmScheduler,
    private val alarmDao: AlarmDao
) : AlarmRepository {
    override suspend fun setAlarm(alarm: Alarm) {
        alarmScheduler.scheduleAlarm(alarm.id, alarm.triggerTime)
        alarmDao.insertAlarm(alarm)
    }

    override suspend fun getAlarm(id: String): Alarm? {
        // ToDo
        return null
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        alarmScheduler.scheduleAlarm(alarm.id, alarm.triggerTime)
        alarmDao.updateAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        alarmScheduler.cancelAlarm(alarm.id)
        alarmDao.deleteAlarm(alarm)
    }

    override suspend fun listenAlarms(): Flow<List<Alarm>> {
        return alarmDao.getAllAlarms().map { alarms ->
            alarms.map { alarm ->
                Alarm(
                    id = alarm.id,
                    triggerTime = alarm.triggerTime,
                    name = alarm.name
                )
            }
        }
    }

    override suspend fun canScheduleAlarm(): Boolean = alarmScheduler.canScheduleExactAlarms()
}