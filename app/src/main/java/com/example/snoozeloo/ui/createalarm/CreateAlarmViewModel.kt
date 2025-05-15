package com.example.snoozeloo.ui.createalarm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.R
import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateAlarmUiState(
    val hourInputField: TimeInputField? = null,
    val minuteInputField: TimeInputField? = null,
    val isAlarmDialogOpen: Boolean = false,
    val alarmName: String = "",
)

data class TimeInputField(
    val time: String,
    val color: Int
)

class CreateAlarmViewModel(
    private val alarmRepository: AlarmRepository
) : ViewModel() {
    private var isHourFocusedOnce = false

    private var isMinuteFocusedOnce = false

    private val _state = MutableStateFlow(CreateAlarmUiState())
    val state = _state.asStateFlow()

    fun hourInputTextHasFocus() {
        _state.update { state ->
            state.copy(
                hourInputField = TimeInputField(
                    time = evaluateTimeInputField(
                        isFocusedOnce = isHourFocusedOnce,
                        currentTime = state.hourInputField?.time
                    ),
                    color = R.color.dodger_blue
                )
            )
        }
        isHourFocusedOnce = true
    }

    fun minuteInputTextHasFocus() {
        _state.update { state ->
            state.copy(
                minuteInputField = TimeInputField(
                    time = evaluateTimeInputField(
                        isFocusedOnce = isMinuteFocusedOnce,
                        currentTime = state.minuteInputField?.time
                    ),
                    color = R.color.dodger_blue
                )
            )
        }
        isMinuteFocusedOnce = true
    }

    fun saveAlarm() {
        viewModelScope.launch {
            val timeInMillis = getTimeInMillis()

            Log.d(TAG, "Saving alarm time in millis: $timeInMillis")

            alarmRepository.setAlarm(
                Alarm(
                    triggerTime = timeInMillis,
                    name = "Alarm",
                    isEnabled = true
                )
            )
        }
    }

    private fun getTimeInMillis(): Long {
        val currentTimeMillis = System.currentTimeMillis()
        val calendarNow = java.util.Calendar.getInstance().apply {
            timeInMillis = currentTimeMillis
        }
        val hour = state.value.hourInputField?.time?.toIntOrNull() ?: 0
        val minute = state.value.minuteInputField?.time?.toIntOrNull() ?: 0

        val calendarTarget = java.util.Calendar.getInstance().apply {
            timeInMillis = currentTimeMillis
            set(java.util.Calendar.HOUR_OF_DAY, hour)
            set(java.util.Calendar.MINUTE, minute)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }

        if (calendarTarget.before(calendarNow)) {
            calendarTarget.add(java.util.Calendar.DAY_OF_YEAR, 1)
        }

        return calendarTarget.timeInMillis
    }

    private fun evaluateTimeInputField(isFocusedOnce: Boolean, currentTime: String?): String {
        currentTime?.let {
            if (isFocusedOnce) {
                return it
            }
        }

        return EMPTY_STRING
    }

    fun onHourInputTextChanged(hour: String) {
        _state.update { state ->
            state.copy(
                hourInputField = TimeInputField(
                    time = hour,
                    color = R.color.dodger_blue
                )
            )
        }
    }

    fun onMinuteInputTextChanged(minute: String) {
        _state.update { state ->
            state.copy(
                minuteInputField = TimeInputField(
                    time = minute,
                    color = R.color.dodger_blue
                )
            )
        }
    }

    fun onAlarmNameDialogOpened() {
        _state.update { state ->
            state.copy(isAlarmDialogOpen = true)
        }
    }

    fun onAlarmNameDialogDismissed() {
        _state.update { state ->
            state.copy(isAlarmDialogOpen = false)
        }
    }

    fun onAlarmNameDialogSaveButtonClicked(alarmName: String) {
        _state.update { state ->
            state.copy(alarmName = alarmName)
        }
    }

    companion object {
        private const val TAG = "CreateAlarmViewModel"
        private const val EMPTY_STRING = ""
    }
}