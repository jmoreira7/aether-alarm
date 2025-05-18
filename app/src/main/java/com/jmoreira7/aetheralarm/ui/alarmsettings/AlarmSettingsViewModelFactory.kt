package com.jmoreira7.aetheralarm.ui.alarmsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jmoreira7.aetheralarm.AetherAlarmApp

class AlarmSettingsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmSettingsViewModel(AetherAlarmApp.appModule.alarmRepository) as T
    }
}