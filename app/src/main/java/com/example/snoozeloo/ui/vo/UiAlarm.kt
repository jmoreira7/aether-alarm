package com.example.snoozeloo.ui.vo

import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.ui.AmPm
import com.example.snoozeloo.ui.getHour
import com.example.snoozeloo.ui.getMinute
import com.example.snoozeloo.ui.timeDifferenceFromNow

data class UiAlarm(
    val name: String,
    val hour: String,
    val minute: String,
    val amPm: AmPm,
    val timeRemaining: String
)

fun Alarm.toUiAlarm(): UiAlarm {
    return UiAlarm(
        name = name,
        hour = getHourText(triggerTime),
        minute = getMinuteText(triggerTime),
        amPm = AmPm.NONE,
        timeRemaining = getTimeRemainingString(triggerTime),
    )
}

private fun getHourText(triggerTime: Long): String {
    return getHour(triggerTime).let { hour ->
        if (hour < 10) "0$hour" else "$hour"
    }
}

private fun getMinuteText(triggerTime: Long): String {
    return getMinute(triggerTime).let { minute ->
        if (minute < 10) "0$minute" else "$minute"
    }
}

private fun getTimeRemainingString(triggerTime: Long): String {
    val timeRemaining = timeDifferenceFromNow(triggerTime)
    val hoursRemaining = getHour(timeRemaining)
    val minutesRemaining = getMinute(timeRemaining)

    return "${hoursRemaining}h ${minutesRemaining}min"
}