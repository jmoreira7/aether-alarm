package com.jmoreira7.aetheralarm.data.framework

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jmoreira7.aetheralarm.receiver.AlarmReceiver

class AlarmScheduler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    fun canScheduleExactAlarms(): Boolean {
        if (alarmManager.canScheduleExactAlarms()) {
            Log.i(TAG, "Can schedule exact alarms.")
            return true
        }

        Log.i(TAG, "Cannot schedule exact alarms. Need to ask permission.")
        return false
    }

    fun scheduleAlarm(alarmId: Int, triggerTime: Long) {
        if (!alarmManager.canScheduleExactAlarms()) {
            Log.d(TAG, "Cannot schedule exact alarms")
            return
        }

        Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_ID_EXTRA, alarmId)
        }.let { intent ->
            PendingIntent.getBroadcast(
                context,
                alarmId,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }.also { pendingIntent ->
            alarmManager.setAlarmClock(
                AlarmClockInfo(
                    triggerTime,
                    pendingIntent
                ),
                pendingIntent
            )
        }
    }

    fun cancelAlarm(alarmId: Int) {
        Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_ID_EXTRA, alarmId)
        }.also { intent ->
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    alarmId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }

    companion object {
        private const val TAG = "AlarmScheduler"
        private const val ALARM_ID_EXTRA = "ALARM_ID"
    }
}