package com.jmoreira7.aetheralarm.ui.alarmtrigger

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.jmoreira7.aetheralarm.databinding.ActivityAlarmTriggerBinding

class AlarmTriggerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmTriggerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAlarmTriggerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}