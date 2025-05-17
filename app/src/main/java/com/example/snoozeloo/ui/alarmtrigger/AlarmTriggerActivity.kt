package com.example.snoozeloo.ui.alarmtrigger

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.snoozeloo.databinding.ActivityAlarmTriggerBinding

class AlarmTriggerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmTriggerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAlarmTriggerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}