package com.example.snoozeloo.ui

import java.util.Calendar

private const val ONE_DAY_IN_HOURS = 24
private const val ONE_HOUR_IN_MINUTES = 60

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

fun timeDifferenceFromNowNormalized(timeMillis: Long): Pair<Int, Int> {
    val now = Calendar.getInstance()
    val target = Calendar.getInstance().apply {
        timeInMillis = timeMillis
        set(Calendar.YEAR, now.get(Calendar.YEAR))
        set(Calendar.MONTH, now.get(Calendar.MONTH))
        set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH))
    }

    return when {
        target.timeInMillis > now.timeInMillis && target.timeInMillis > now.timeInMillis -> {
            return Pair(
                target.get(Calendar.HOUR_OF_DAY) - now.get(Calendar.HOUR_OF_DAY),
                target.get(Calendar.MINUTE) - now.get(Calendar.MINUTE)
            )
        }

        target.timeInMillis > now.timeInMillis && target.timeInMillis < now.timeInMillis -> {
            return Pair(
                target.get(Calendar.HOUR_OF_DAY) - now.get(Calendar.HOUR_OF_DAY),
                (target.get(Calendar.MINUTE) + ONE_HOUR_IN_MINUTES) - now.get(Calendar.MINUTE)
            )
        }

        target.timeInMillis < now.timeInMillis && target.timeInMillis < now.timeInMillis -> {
            return Pair(
                (target.get(Calendar.HOUR_OF_DAY) + ONE_DAY_IN_HOURS) - now.get(Calendar.HOUR_OF_DAY),
                (target.get(Calendar.MINUTE) + ONE_HOUR_IN_MINUTES) - now.get(Calendar.MINUTE)
            )
        }

        target.timeInMillis < now.timeInMillis && target.timeInMillis > now.timeInMillis -> {
            return Pair(
                (target.get(Calendar.HOUR_OF_DAY) + ONE_DAY_IN_HOURS) - now.get(Calendar.HOUR_OF_DAY),
                target.get(Calendar.MINUTE) - now.get(Calendar.MINUTE)
            )
        }

        else -> {
            Pair(0, 0)
        }
    }
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