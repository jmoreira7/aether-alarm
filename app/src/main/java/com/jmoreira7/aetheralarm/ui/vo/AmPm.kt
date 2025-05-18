package com.jmoreira7.aetheralarm.ui.vo

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