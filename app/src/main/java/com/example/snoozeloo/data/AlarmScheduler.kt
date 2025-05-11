package com.example.snoozeloo.data

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmScheduler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    fun canScheduleExactAlarms(): Boolean {
        if (alarmManager.canScheduleExactAlarms()) {
            Log.i("AlarmScheduler", "Can schedule exact alarms.")
            return true
        }

        Log.i("AlarmScheduler", "Cannot schedule exact alarms. Need to ask permission.")
        return false
    }

    fun scheduleAlarm(intent: Intent) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmInfo = AlarmClockInfo(
            System.currentTimeMillis() + 10000,
            pendingIntent
        )

        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setAlarmClock(
                alarmInfo,
                pendingIntent
            )
        } else {
            Log.d("AlarmScheduler", "Cannot schedule exact alarms")
        }
    }
}