package com.example.snoozeloo.ui.createalarm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.snoozeloo.databinding.ActivityCreateAlarmBinding
import kotlinx.coroutines.launch

class CreateAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAlarmBinding

    private val viewModel: CreateAlarmViewModel by viewModels {
        CreateAlarmViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        setupHourTextInputView()
        setupMinuteTextInputView()
        setupButtons()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.run {
                        hourInputField?.let { handleHourInputText(it) }
                        minuteInputField?.let { handleMinuteInputText(it) }
                        handleAlarmNameText(alarmName)
                    }
                }
            }
        }
    }

    private fun setupHourTextInputView() {
        binding.activityCreateAlarmHourTextInput.run {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    viewModel.hourInputTextHasFocus()
                }
            }

            addTextChangedListener { hour ->
                viewModel.onHourInputTextChanged(hour.toString())
            }
        }
    }

    private fun setupMinuteTextInputView() {
        binding.activityCreateAlarmMinuteTextInput.run {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    viewModel.minuteInputTextHasFocus()
                }
            }

            addTextChangedListener { minute ->
                viewModel.onMinuteInputTextChanged(minute.toString())
            }
        }
    }

    private fun setupButtons() {
        binding.activityCreateAlarmBackButton.setOnClickListener {
            finish()
        }

        binding.activityCreateAlarmSaveButton.setOnClickListener {
            viewModel.saveAlarm()
        }

        binding.activityCreateAlarmSecondaryTile.setOnClickListener {
            AlarmNameDialog().show(supportFragmentManager, "AlarmNameDialog")
        }
    }

    private fun handleHourInputText(hour: TimeInputField) {
        binding.activityCreateAlarmHourTextInput.run {
            setTextColor(resources.getColor(hour.color, null))
            if (text.toString() != hour.time) {
                setText(hour.time)
            }
        }
    }

    private fun handleMinuteInputText(minute: TimeInputField) {
        binding.activityCreateAlarmMinuteTextInput.run {
            setTextColor(resources.getColor(minute.color, null))
            if (text.toString() != minute.time) {
                setText(minute.time)
            }
        }
    }

    private fun handleAlarmNameText(alarmName: String) {
        binding.activityCreateAlarmName.text = alarmName
    }
}