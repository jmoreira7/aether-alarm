package com.example.snoozeloo.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.snoozeloo.data.alarm.AlarmScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private var alarmScheduler: AlarmScheduler? = null

    private val _state = MutableStateFlow(MainViewModelState())

    val state: StateFlow<MainViewModelState> = _state.asStateFlow()

    fun setAlarmScheduler(alarmScheduler: AlarmScheduler) {
        this.alarmScheduler = alarmScheduler
    }

    fun scheduleAlarm(intent: Intent) {
        alarmScheduler?.scheduleAlarm(intent)
    }
}