package com.jmoreira7.aetheralarm.ui

import java.util.Calendar

fun getHour(timeMillis: Long): Int {
    return Calendar.getInstance().apply {
        timeInMillis = timeMillis
    }.get(Calendar.HOUR_OF_DAY)
}

fun getMinute(timeMillis: Long): Int {
    return Calendar.getInstance().apply {
        timeInMillis = timeMillis
    }.get(Calendar.MINUTE)
}

fun getNextOccurrence(timeMillis: Long): Long {
    val now = Calendar.getInstance()
    val target = Calendar.getInstance().apply {
        timeInMillis = now.timeInMillis
        set(Calendar.HOUR_OF_DAY, getHour(timeMillis))
        set(Calendar.MINUTE, getMinute(timeMillis))
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    if (target.timeInMillis < now.timeInMillis) {
        target.add(Calendar.DAY_OF_MONTH, 1)
    }

    return target.timeInMillis
}

fun getHourTimeRemaining(triggerTime: Long): Int {
    return timeDifferenceFromNowNormalized(triggerTime).first
}

fun getMinuteTimeRemaining(triggerTime: Long): Int {
    return timeDifferenceFromNowNormalized(triggerTime).second
}

private fun timeDifferenceFromNowNormalized(timeMillis: Long): Pair<Int, Int> {
    val now = Calendar.getInstance().timeInMillis
    val diffMillis = timeMillis - now

    if (diffMillis <= 0) return Pair(0, 0)

    val diffMinutes = diffMillis / (60 * 1000)
    val hours = (diffMinutes / 60).toInt()
    val minutes = (diffMinutes % 60).toInt()

    return Pair(hours, minutes)
}