package com.example.snoozeloo.data.source.androidapi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.snoozeloo.data.AlarmRepository
import com.example.snoozeloo.data.DefaultAlarmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver(private val alarmRepository: AlarmRepository) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "onReceive called with intent: $intent")

//        context?.let {
//            val newIntent = Intent(it, AlarmTriggerScreen::class.java).apply {
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            }
//
//            it.startActivity(newIntent)
//        }

        // Improve this code. Maybe use a callback to the repository.
        CoroutineScope(Dispatchers.IO).launch {
            (alarmRepository as DefaultAlarmRepository).triggerAlarm()
        }
    }
}