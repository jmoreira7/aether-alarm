package com.example.snoozeloo.data

import com.example.snoozeloo.domain.entity.Alarm

fun Alarm.toAndroidAlarm(): AndroidAlarm {
    return AndroidAlarm(
        time = triggerTime,
    )
}

fun AndroidAlarm.toExternal(): Alarm {
    return Alarm(
        triggerTime = time,
        isActive = true,
        label = null,
    )
}