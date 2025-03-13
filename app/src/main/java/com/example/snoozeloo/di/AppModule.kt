package com.example.snoozeloo.di

import android.app.AlarmManager
import com.example.snoozeloo.data.AlarmRepository
import com.example.snoozeloo.data.source.platform.AlarmScheduler

interface AppModule {
    val alarmRepository: AlarmRepository
    val alarmManager: AlarmManager
    val alarmScheduler: AlarmScheduler
}