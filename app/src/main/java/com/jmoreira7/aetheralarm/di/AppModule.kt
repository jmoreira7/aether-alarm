package com.jmoreira7.aetheralarm.di

import android.app.AlarmManager
import com.jmoreira7.aetheralarm.data.database.AlarmDatabase
import com.jmoreira7.aetheralarm.domain.repository.AlarmRepository
import com.jmoreira7.aetheralarm.data.framework.AlarmScheduler

interface AppModule {
    val alarmRepository: AlarmRepository
    val alarmManager: AlarmManager
    val alarmScheduler: AlarmScheduler
    val alarmDatabase: AlarmDatabase
}