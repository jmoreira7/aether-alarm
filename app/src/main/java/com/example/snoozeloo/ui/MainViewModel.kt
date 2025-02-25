package com.example.snoozeloo.ui

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.snoozeloo.data.source.androidapi.AlarmScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MainUiState(
    val dummyState: Boolean = false
)

class MainViewModel : ViewModel() {

    private var alarmScheduler: AlarmScheduler? = null

    private val _state = MutableStateFlow(MainUiState())

    val state: StateFlow<MainUiState> = _state.asStateFlow()

    fun setAlarmScheduler(alarmScheduler: AlarmScheduler) {
        this.alarmScheduler = alarmScheduler
    }

    fun scheduleAlarm(intent: Intent) {
        alarmScheduler?.scheduleAlarm(intent)
    }
}