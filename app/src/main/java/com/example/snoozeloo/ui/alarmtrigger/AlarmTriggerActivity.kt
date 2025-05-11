package com.example.snoozeloo.ui.alarmtrigger

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.snoozeloo.R

class AlarmTriggerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("AlarmTriggerActivity", "Alarm triggered activity created!")
        enableEdgeToEdge()
        setContentView(R.layout.activity_alarm_trigger)

    }
}