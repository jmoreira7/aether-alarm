package com.example.snoozeloo.ui

fun getHour(timeMillis: Long): Int {
    return java.util.Calendar.getInstance().apply {
        timeInMillis = timeMillis
    }.get(java.util.Calendar.HOUR_OF_DAY)
}

fun getMinute(timeMillis: Long): Int {
    return java.util.Calendar.getInstance().apply {
        timeInMillis = timeMillis
    }.get(java.util.Calendar.MINUTE)
}

fun timeDifferenceFromNow(timeMillis: Long): Long {
    return timeMillis - System.currentTimeMillis()
}