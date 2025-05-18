package com.jmoreira7.aetheralarm

import android.app.Application
import com.jmoreira7.aetheralarm.di.AppModule
import com.jmoreira7.aetheralarm.di.AppModuleImpl

class AetherAlarmApp : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}
