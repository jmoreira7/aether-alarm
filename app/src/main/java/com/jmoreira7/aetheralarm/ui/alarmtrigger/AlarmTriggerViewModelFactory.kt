package com.jmoreira7.aetheralarm.ui.alarmtrigger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jmoreira7.aetheralarm.AetherAlarmApp

class AlarmTriggerViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmTriggerViewModel(AetherAlarmApp.appModule.alarmRepository) as T
    }
}