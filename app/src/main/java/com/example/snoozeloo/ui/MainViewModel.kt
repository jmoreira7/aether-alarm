package com.example.snoozeloo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.domain.repository.AlarmRepository
import com.example.snoozeloo.ui.vo.UiAlarm
import com.example.snoozeloo.ui.vo.toUiAlarm
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val alarmItems: List<UiAlarm> = emptyList(),
)

class MainViewModel(
    private val alarmRepository: AlarmRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private val _router = Channel<Router>()
    val router = _router.receiveAsFlow()

    init {
        checkAlarmPermission()
        listenAlarms()
    }

    fun onAlarmItemSwitchToggled(id: Int, isChecked: Boolean) {
        viewModelScope.launch {
            alarmRepository.getAlarm(id)?.let { alarm ->
                val updatedAlarm = if (isChecked) {
                    alarm.copy(
                        triggerTime = getNextOccurrence(alarm.triggerTime),
                        isEnabled = true
                    )
                } else {
                    alarm.copy(isEnabled = false)
                }

                alarmRepository.updateAlarm(updatedAlarm)
            }
        }
    }

    private fun checkAlarmPermission() {
        viewModelScope.launch {
            if (!alarmRepository.canScheduleAlarm()) {
                sendRouteEvent(Router.AlarmPermission)
            }
        }
    }

    private fun listenAlarms() {
        viewModelScope.launch {
            alarmRepository.listenAlarms().map { alarms ->
                alarms.map { it.toUiAlarm() }
            }
                .collect { uiAlarms ->
                    _state.value = _state.value.copy(alarmItems = uiAlarms)
                }
        }
    }

    private fun sendRouteEvent(event: Router) {
        viewModelScope.launch {
            _router.send(event)
        }
    }
}