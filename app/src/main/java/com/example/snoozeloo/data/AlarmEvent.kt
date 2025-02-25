package com.example.snoozeloo.data

sealed class AlarmEvent {
    data object Triggered : AlarmEvent()
}