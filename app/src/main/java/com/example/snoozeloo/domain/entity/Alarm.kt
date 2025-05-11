package com.example.snoozeloo.domain.entity

import java.util.UUID

data class Alarm(
    val id: UUID = UUID.randomUUID(),
    val time: Long,
    val isActive: Boolean,
    val label: String?,
)