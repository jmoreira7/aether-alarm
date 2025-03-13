package com.example.snoozeloo.di

import android.app.AlarmManager
import android.content.Context
import com.example.snoozeloo.data.AlarmRepository
import com.example.snoozeloo.data.DefaultAlarmRepository
import com.example.snoozeloo.data.source.platform.AlarmScheduler

class AppModuleImpl(
    private val appContext: Context
) : AppModule {
    override val alarmRepository: AlarmRepository by lazy {
        DefaultAlarmRepository(alarmScheduler)
    }

    override val alarmManager: AlarmManager by lazy {
        appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override val alarmScheduler: AlarmScheduler by lazy {
        AlarmScheduler(appContext, alarmManager)
    }
}