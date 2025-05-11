package com.example.snoozeloo.ui.createalarm

import androidx.lifecycle.ViewModel
import com.example.snoozeloo.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CreateAlarmUiState(
    val hourInputField: TimeInputField? = null,
    val minuteInputField: TimeInputField? = null
)

data class TimeInputField(
    val time: String,
    val color: Int
)

class CreateAlarmViewModel : ViewModel() {
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

    companion object {
        private const val EMPTY_STRING = ""
    }
}