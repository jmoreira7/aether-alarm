package com.jmoreira7.aetheralarm.ui.vo

import com.jmoreira7.aetheralarm.domain.entity.Alarm
import com.jmoreira7.aetheralarm.ui.getHour
import com.jmoreira7.aetheralarm.ui.getHourTimeRemaining
import com.jmoreira7.aetheralarm.ui.getMinute
import com.jmoreira7.aetheralarm.ui.getMinuteTimeRemaining

data class UiAlarm(
    val id: Int,
    val name: String,
    val hour: String,
    val minute: String,
    val amPm: AmPm,
    val hourTimeRemaining: String,
    val minuteTimeRemaining: String,
    val isEnabled: Boolean = true
)

fun Alarm.toUiAlarm(): UiAlarm {
    return UiAlarm(
        id = id,
        name = name,
        hour = getHourText(triggerTime),
        minute = getMinuteText(triggerTime),
        amPm = getAmPm(triggerTime),
        hourTimeRemaining = getHourTimeRemaining(triggerTime).toString(),
        minuteTimeRemaining = getMinuteTimeRemaining(triggerTime).toString(),
        isEnabled = isEnabled
    )
}

private fun getHourText(triggerTime: Long): String {
    return getHour(triggerTime).let { hour ->
        when (hour) {
            in 0..9 -> "0$hour"
            in 10..12 -> "$hour"
            in 13..19 -> "0${hour - 12}"
            in 20..23 -> "${hour - 12}"
            else -> ""
        }
    }
}

private fun getMinuteText(triggerTime: Long): String {
    return getMinute(triggerTime).let { minute ->
        if (minute < 10) "0$minute" else "$minute"
    }
}

private fun getAmPm(triggerTime: Long): AmPm {
    return getHour(triggerTime).let { hour ->
        when (hour) {
            in 0..11 -> AmPm.AM
            in 12..23 -> AmPm.PM
            else -> AmPm.NONE
        }
    }
}