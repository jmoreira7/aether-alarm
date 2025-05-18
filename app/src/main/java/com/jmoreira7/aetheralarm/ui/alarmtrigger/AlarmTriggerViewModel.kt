package com.jmoreira7.aetheralarm.ui.alarmtrigger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmoreira7.aetheralarm.domain.entity.Alarm
import com.jmoreira7.aetheralarm.domain.repository.AlarmRepository
import com.jmoreira7.aetheralarm.ui.getHourText
import com.jmoreira7.aetheralarm.ui.getMinuteText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AlarmTriggerUiState(
    private val timeInMillis: Long = 0L,
    val alarmName: String = "",
) {
    val alarmTime: String
        get() = "${getHourText(timeInMillis, false)}:${getMinuteText(timeInMillis)}"
}

class AlarmTriggerViewModel(
    private val alarmRepository: AlarmRepository
) : ViewModel() {
    private var alarm: Alarm? = null

    private val _state = MutableStateFlow(AlarmTriggerUiState())
    val state = _state.asStateFlow()

    fun setup(alarmId: Int) {
        viewModelScope.launch {
            alarmRepository.getAlarm(alarmId)?.let { alarm ->
                Log.d("AlarmTriggerViewModel", "Alarm: $alarm")

                this@AlarmTriggerViewModel.alarm = alarm

                _state.update {
                    AlarmTriggerUiState(
                        timeInMillis = alarm.triggerTime,
                        alarmName = alarm.name
                    )
                }
            }
        }

    }

    fun triggerOffButtonClicked() {
        viewModelScope.launch {
            alarm?.let { alarm ->
                alarmRepository.updateAlarm(
                    alarm.copy(
                        isEnabled = false
                    )
                )
            }
        }
    }
}