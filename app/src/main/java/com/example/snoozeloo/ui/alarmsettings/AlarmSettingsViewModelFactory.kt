package com.example.snoozeloo.ui.alarmsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snoozeloo.SnoozelooApp

class AlarmSettingsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmSettingsViewModel(SnoozelooApp.appModule.alarmRepository) as T
    }
}