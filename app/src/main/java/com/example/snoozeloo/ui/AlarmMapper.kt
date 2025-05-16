package com.example.snoozeloo.ui

import com.example.snoozeloo.domain.entity.Alarm
import com.example.snoozeloo.ui.vo.UiAlarm

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
        if (hour < 10) {
            "0$hour"
        } else {
            "$hour"
        }
    }
}

private fun getMinuteText(triggerTime: Long): String {
    return getMinute(triggerTime).let { minute ->
        if (minute < 10) {
            "0$minute"
        } else {
            "$minute"
        }
    }
}

private fun getTimeRemainingString(triggerTime: Long): String {
    val timeRemaining = timeDifferenceFromNow(triggerTime)
    val hoursRemaining = getHour(timeRemaining)
    val minutesRemaining = getMinute(timeRemaining)

    return "${hoursRemaining}h ${minutesRemaining}min"
}