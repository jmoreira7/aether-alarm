package com.example.snoozeloo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snoozeloo.SnoozelooApp

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(SnoozelooApp.appModule.alarmRepository) as T
    }
}