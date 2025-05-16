package com.example.snoozeloo.ui.vo

import com.example.snoozeloo.ui.AmPm

data class UiAlarm(
    val name: String,
    val hour: String,
    val minute: String,
    val amPm: AmPm,
    val timeRemaining: String
)
