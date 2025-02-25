package com.example.snoozeloo.data

import java.util.UUID

data class Alarm(
    val id: UUID = UUID.randomUUID(),
    val time: Long,
    val isActive: Boolean,
    val label: String?,
)