package com.jmoreira7.aetheralarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jmoreira7.aetheralarm.ui.alarmtrigger.AlarmTriggerActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive called with intent: $intent")

        intent?.getIntExtra(ALARM_ID_EXTRA, -1)?.let { alarmId ->
            Intent(context, AlarmTriggerActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(ALARM_ID_EXTRA, alarmId)
            }
        }.also { newIntent ->
            context?.startActivity(newIntent)
        }
    }

    companion object {
        private const val TAG = "AlarmReceiver"
        const val ALARM_ID_EXTRA = "ALARM_ID"
    }
}