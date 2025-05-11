package com.example.snoozeloo.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.domain.repository.AlarmRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val isLoading: Boolean = false
)

class MainViewModel(
    private val alarmRepository: AlarmRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private val _router = Channel<Router>()
    val router = _router.receiveAsFlow()

    init {
        Log.d("MainViewModel", "init")
        checkAlarmPermission()
    }

    // Test. Need to improve
    fun scheduleAlarm() {
        viewModelScope.launch {
            alarmRepository.setAlarm(
                Alarm(
                    triggerTime = 0,
                    isActive = true,
                    label = null
                )
            )
        }
    }

    private fun checkAlarmPermission() {
        if (!alarmRepository.canScheduleAlarm()) {
            sendRouteEvent(Router.AlarmPermission)
        }
    }

    private fun sendRouteEvent(event: Router) {
        viewModelScope.launch {
            _router.send(event)
        }
    }
}