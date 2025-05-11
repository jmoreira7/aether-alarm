package com.example.snoozeloo.di

import android.app.AlarmManager
import com.example.snoozeloo.domain.repository.AlarmRepository
import com.example.snoozeloo.data.AlarmScheduler

interface AppModule {
    val alarmRepository: AlarmRepository
    val alarmManager: AlarmManager
    val alarmScheduler: AlarmScheduler
}