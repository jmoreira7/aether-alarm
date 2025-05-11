package com.example.snoozeloo.data

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.receiver.AlarmReceiver

class AlarmScheduler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    private val receiverIntent = Intent(context, AlarmReceiver::class.java)

    fun canScheduleExactAlarms(): Boolean {
        if (alarmManager.canScheduleExactAlarms()) {
            Log.i(TAG, "Can schedule exact alarms.")
            return true
        }

        Log.i(TAG, "Cannot schedule exact alarms. Need to ask permission.")
        return false
    }

    fun scheduleAlarm(alarmId: Int, triggerTime: Long) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            receiverIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setAlarmClock(
                AlarmClockInfo(
                    triggerTime,
                    pendingIntent
                ),
                pendingIntent
            )
        } else {
            Log.d(TAG, "Cannot schedule exact alarms")
        }
    }

    fun cancelAlarm(alarmId: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmId,
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    companion object {
        private const val TAG = "AlarmScheduler"
    }
}