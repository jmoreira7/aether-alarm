package com.example.snoozeloo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.domain.repository.AlarmRepository
import com.example.snoozeloo.ui.vo.UiAlarm
import com.example.snoozeloo.ui.vo.toUiAlarm
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
        setAlarmItemsTimeRemainingUpdater()
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

    // This implementation can be improved to use a better way to update the time remaining
    private fun setAlarmItemsTimeRemainingUpdater() {
        viewModelScope.launch {
            while (true) {
                delay(60000L)
                _state.value = _state.value.copy(
                    alarmItems = _state.value.alarmItems.map { uiAlarm ->
                        val updatedTimeRemaining = getUpdatedTimeRemaining(
                            uiAlarm.hourTimeRemaining,
                            uiAlarm.minuteTimeRemaining
                        )

                        uiAlarm.copy(
                            hourTimeRemaining = updatedTimeRemaining.first,
                            minuteTimeRemaining = updatedTimeRemaining.second
                        )
                    }
                )
            }
        }
    }

    private fun getUpdatedTimeRemaining(
        hourTimeRemaining: String,
        minuteTimeRemaining: String
    ): Pair<String, String> {
        val hour = hourTimeRemaining.toInt()
        val minute = minuteTimeRemaining.toInt()

        return if (minute > 0) {
            Pair("$hour", "${(minute - 1)}")
        } else {
            Pair("${(hour - 1)}", "$MAX_MINUTES_IN_HOUR")
        }
    }

    companion object {
        private const val MAX_MINUTES_IN_HOUR = 59
    }
}