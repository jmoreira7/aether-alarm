package com.jmoreira7.aetheralarm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jmoreira7.aetheralarm.AetherAlarmApp

class MainViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(AetherAlarmApp.appModule.alarmRepository) as T
    }
}