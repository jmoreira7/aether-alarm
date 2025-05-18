package com.jmoreira7.aetheralarm.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey
    val id: Int = UUID.randomUUID().hashCode(),
    val triggerTime: Long,
    val name: String,
    val isEnabled: Boolean
)