package com.jmoreira7.aetheralarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jmoreira7.aetheralarm.ui.alarmtrigger.AlarmTriggerActivity

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