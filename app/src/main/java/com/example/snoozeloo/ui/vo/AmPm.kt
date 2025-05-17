package com.example.snoozeloo.ui.vo

enum class AmPm {
    AM,
    PM,
    NONE
}


fun AmPm.toText(): String {
    return when (this) {
        AmPm.AM -> "AM"
        AmPm.PM -> "PM"
        AmPm.NONE -> ""
    }
}