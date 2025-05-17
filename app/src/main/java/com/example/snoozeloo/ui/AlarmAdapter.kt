package com.example.snoozeloo.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snoozeloo.R
import com.example.snoozeloo.ui.vo.UiAlarm

class AlarmAdapter(
    private val onSwitchToggled: (Int, Boolean) -> Unit
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
        holder.timeRemaining.text =
            "Alarm in ${alarm.hourTimeRemaining}h ${alarm.minuteTimeRemaining}min"
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
