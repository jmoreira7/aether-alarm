package com.example.snoozeloo.domain.entity

import java.util.UUID

data class Alarm(
    val id: Int = UUID.randomUUID().hashCode(),
    val triggerTime: Long,
    val name: String,
)