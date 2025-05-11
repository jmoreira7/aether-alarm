package com.example.snoozeloo.di

import android.app.AlarmManager
import android.content.Context
import androidx.room.Room
import com.example.snoozeloo.data.AlarmDatabase
import com.example.snoozeloo.domain.repository.AlarmRepository
import com.example.snoozeloo.data.DefaultAlarmRepository
import com.example.snoozeloo.data.AlarmScheduler

class AppModuleImpl(
    private val appContext: Context
) : AppModule {
    override val alarmRepository: AlarmRepository by lazy {
        DefaultAlarmRepository(alarmScheduler, alarmDatabase.alarmDao())
    }

    override val alarmManager: AlarmManager by lazy {
        appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override val alarmScheduler: AlarmScheduler by lazy {
        AlarmScheduler(appContext, alarmManager)
    }

    override val alarmDatabase: AlarmDatabase by lazy {
        Room.databaseBuilder(
            context = appContext,
            klass = AlarmDatabase::class.java,
            "alarm_database"
        ).build()
    }
}