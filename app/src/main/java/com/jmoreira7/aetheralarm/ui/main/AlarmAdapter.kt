package com.jmoreira7.aetheralarm.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmoreira7.aetheralarm.R
import com.jmoreira7.aetheralarm.ui.vo.UiAlarm
import com.jmoreira7.aetheralarm.ui.vo.toText

class AlarmAdapter(
    private val onAlarmItemClicked: (
        alarmId: Int,
        alarmName: String,
        alarmHour: String,
        alarmMinute: String
    ) -> Unit,
    private val onSwitchToggled: (alarmId: Int, isChecked: Boolean) -> Unit,
    private val onDeleteClicked: (alarmId: Int) -> Unit
) : RecyclerView.Adapter<AlarmViewHolder>() {
    private var alarms: List<UiAlarm> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_item, parent, false)
        return AlarmViewHolder(view)
    }

    override fun getItemCount(): Int = alarms.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]

        holder.title.text = alarm.name

        holder.time.text = "${alarm.hour}:${alarm.minute}"

        holder.amPm.text = alarm.amPm.toText()

        holder.timeRemaining.apply {
            text = "Alarm in ${alarm.hourTimeRemaining}h ${alarm.minuteTimeRemaining}min"
            visibility = if (alarm.isEnabled) View.VISIBLE else View.INVISIBLE
        }

        holder.enableSwitch.apply {
            isChecked = alarm.isEnabled

            setOnClickListener {
                Log.d(
                    TAG,
                    "Switch toggled for alarm: ${alarm.name}, isChecked: $isChecked"
                )

                onSwitchToggled(alarm.id, isChecked)
            }
        }

        holder.deleteButton.setOnClickListener {
            Log.d(TAG, "Delete button clicked for alarm: ${alarm.name}")

            onDeleteClicked(alarm.id)
        }

        holder.itemView.setOnClickListener {
            Log.d(TAG, "Alarm item clicked. [Name: ${alarm.name}], [Id: ${alarm.id}]")

            onAlarmItemClicked(alarm.id, alarm.name, alarm.hour, alarm.minute)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAlarmItems(alarmItems: List<UiAlarm>) {
        alarms = alarmItems
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "AlarmAdapter"
    }
}
