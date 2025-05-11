package com.example.snoozeloo.ui.createalarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snoozeloo.SnoozelooApp

class CreateAlarmViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateAlarmViewModel(SnoozelooApp.appModule.alarmRepository) as T
    }
}