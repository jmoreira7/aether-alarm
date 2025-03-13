package com.example.snoozeloo

import android.app.Application
import com.example.snoozeloo.di.AppModule
import com.example.snoozeloo.di.AppModuleImpl

class SnoozelooApp : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}
