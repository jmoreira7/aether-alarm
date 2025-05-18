package com.jmoreira7.aetheralarm.di

import android.app.AlarmManager
import android.content.Context
import androidx.room.Room
import com.jmoreira7.aetheralarm.data.database.AlarmDatabase
import com.jmoreira7.aetheralarm.data.framework.AlarmScheduler
import com.jmoreira7.aetheralarm.data.repository.DefaultAlarmRepository
import com.jmoreira7.aetheralarm.domain.repository.AlarmRepository

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
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}