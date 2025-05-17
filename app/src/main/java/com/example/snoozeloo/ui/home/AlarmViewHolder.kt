package com.example.snoozeloo.ui.home

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snoozeloo.databinding.AlarmItemBinding
import com.google.android.material.materialswitch.MaterialSwitch

class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding: AlarmItemBinding = AlarmItemBinding.bind(itemView)
    val title: TextView = binding.alarmItemTitle
    val time: TextView = binding.alarmItemHour
    val amPm: TextView = binding.alarmItemAmPm
    val timeRemaining: TextView = binding.alarmItemTimeRemaining
    val enableSwitch: MaterialSwitch = binding.alarmItemSwitch
    val deleteButton: ImageButton = binding.alarmItemDeleteButton
}