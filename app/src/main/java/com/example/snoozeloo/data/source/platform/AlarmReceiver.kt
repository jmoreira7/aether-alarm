package com.example.snoozeloo.data.source.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.snoozeloo.ui.alarmtrigger.AlarmTriggerScreen

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "onReceive called with intent: $intent")

        context?.let {
            val newIntent = Intent(it, AlarmTriggerScreen::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            it.startActivity(newIntent)
        }

        // Improve this code. Maybe use a callback to the repository.
//        CoroutineScope(Dispatchers.IO).launch {
//            (alarmRepository as DefaultAlarmRepository).triggerAlarm()
//        }
    }
}