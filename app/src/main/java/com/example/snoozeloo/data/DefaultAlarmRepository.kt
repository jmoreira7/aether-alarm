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

    override suspend fun getAlarm(id: Int): Alarm? {
        return alarmDao.getAlarmById(id)?.let { alarmEntity ->
            Alarm(
                id = alarmEntity.id,
                triggerTime = alarmEntity.triggerTime,
                name = alarmEntity.name,
                isEnabled = alarmEntity.isEnabled
            )
        }
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        if (alarm.isEnabled) {
            alarmScheduler.scheduleAlarm(alarm.id, alarm.triggerTime)
        } else {
            alarmScheduler.cancelAlarm(alarm.id)
        }
        alarmDao.updateAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarmId: Int) {
        alarmScheduler.cancelAlarm(alarmId)
        alarmDao.deleteAlarm(alarmId)
    }

    override suspend fun listenAlarms(): Flow<List<Alarm>> {
        return alarmDao.getAllAlarms().map { alarms ->
            alarms.map { alarm ->
                Alarm(
                    id = alarm.id,
                    triggerTime = alarm.triggerTime,
                    name = alarm.name,
                    isEnabled = alarm.isEnabled
                )
            }
        }
    }

    override suspend fun canScheduleAlarm(): Boolean = alarmScheduler.canScheduleExactAlarms()
}