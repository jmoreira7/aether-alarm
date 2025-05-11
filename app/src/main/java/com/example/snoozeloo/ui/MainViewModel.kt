package com.example.snoozeloo.ui

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainUiState(
    val isRequestingAlarmPermission: Boolean = false
)

class MainViewModel(
    private val alarmRepository: AlarmRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())

    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        checkAlarmPermission()
    }

    // Test. Need to improve
    fun scheduleAlarm(intent: Intent) {
        viewModelScope.launch {
            alarmRepository.setAlarm(
                Alarm(
                    time = 0,
                    isActive = true,
                    label = null
                ), intent
            )
        }
    }

    fun checkAlarmPermission() {
        if (!alarmRepository.canScheduleAlarm()) {
            _state.update { it.copy(isRequestingAlarmPermission = true) }
        }
    }

    fun isRequestingAlarmPermissionHandled() {
        _state.update { it.copy(isRequestingAlarmPermission = false) }
    }
}