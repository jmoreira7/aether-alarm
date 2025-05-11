package com.example.snoozeloo.ui.createalarm

import androidx.lifecycle.ViewModel
import com.example.snoozeloo.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.sql.Time

data class CreateAlarmUiState(
    val hourInputField: TimeInputField? = null,
    val minuteInputField: TimeInputField? = null
)

data class TimeInputField(
    val time: String,
    val color: Int
)

class CreateAlarmViewModel : ViewModel() {
    private val _state = MutableStateFlow(CreateAlarmUiState())
    val state = _state.asStateFlow()

    fun hourInputTextHasFocus() {
        _state.update { state ->
            state.copy(
                hourInputField = TimeInputField(
                    time = EMPTY_STRING,
                    color = R.color.dodger_blue
                )
            )
        }
    }

    fun minuteInputTextHasFocus() {
        _state.update { state ->
            state.copy(
                minuteInputField = TimeInputField(
                    time = EMPTY_STRING,
                    color = R.color.dodger_blue
                )
            )
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}