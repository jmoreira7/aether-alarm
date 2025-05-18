package com.jmoreira7.aetheralarm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmoreira7.aetheralarm.domain.entity.Alarm
import com.jmoreira7.aetheralarm.domain.repository.AlarmRepository
import com.jmoreira7.aetheralarm.ui.Router
import com.jmoreira7.aetheralarm.ui.getHourTimeRemaining
import com.jmoreira7.aetheralarm.ui.getMinuteTimeRemaining
import com.jmoreira7.aetheralarm.ui.getNextOccurrence
import com.jmoreira7.aetheralarm.ui.vo.UiAlarm
import com.jmoreira7.aetheralarm.ui.vo.toUiAlarm
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val alarmItems: List<UiAlarm> = emptyList(),
)

class MainViewModel(
    private val alarmRepository: AlarmRepository
) : ViewModel() {
    private var alarms: List<Alarm> = emptyList()

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private val _router = Channel<Router>()
    val router = _router.receiveAsFlow()

    init {
        checkAlarmPermission()
        listenAlarms()
        setAlarmItemsTimeRemainingUpdater()
    }

    fun alarmItemSwitchToggled(alarmId: Int, isChecked: Boolean) {
        viewModelScope.launch {
            alarmRepository.getAlarm(alarmId)?.let { alarm ->
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

    fun alarmItemDeleteButtonClicked(alarmId: Int) {
        viewModelScope.launch {
            alarmRepository.deleteAlarm(alarmId)
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
            alarmRepository.listenAlarms()
                .onEach { alarms -> this@MainViewModel.alarms = alarms }
                .map { alarms -> alarms.map { alarm -> alarm.toUiAlarm() } }
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

    private fun setAlarmItemsTimeRemainingUpdater() {
        viewModelScope.launch {
            while (true) {
                delay(THIRTY_SECONDS)
                _state.value = _state.value.copy(
                    alarmItems = _state.value.alarmItems.map { uiAlarm ->
                        uiAlarm.copy(
                            hourTimeRemaining = getHourTimeRemaining(
                                alarms.first { it.id == uiAlarm.id }.triggerTime
                            ).toString(),
                            minuteTimeRemaining = getMinuteTimeRemaining(
                                alarms.first { it.id == uiAlarm.id }.triggerTime
                            ).toString()
                        )
                    }
                )
            }
        }
    }

    companion object {
        private const val THIRTY_SECONDS = 30_000L
    }
}