package com.example.snoozeloo.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.snoozeloo.presentation.activity.AlarmTriggerActivity
import com.example.snoozeloo.presentation.activity.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "onReceive called with intent: $intent")

        context?.let {
            val newIntent = Intent(it, AlarmTriggerActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            it.startActivity(newIntent)
        }


    }
}