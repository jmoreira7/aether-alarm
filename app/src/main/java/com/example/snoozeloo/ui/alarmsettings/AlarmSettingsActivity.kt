package com.example.snoozeloo.ui.alarmsettings

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.snoozeloo.databinding.ActivityAlarmSettingsBinding
import kotlinx.coroutines.launch

class AlarmSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmSettingsBinding

    private val viewModel: CreateAlarmViewModel by viewModels {
        AlarmSettingsViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAlarmSettingsBinding.inflate(layoutInflater)
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
                        handleDialogState(isAlarmDialogOpen)
                        handleAlarmNameText(alarmName)
                    }
                }
            }
        }
    }

    private fun setupHourTextInputView() {
        binding.activityAlarmSettingsHourTextInput.run {
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
        binding.activityAlarmSettingsMinuteTextInput.run {
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
        binding.activityAlarmSettingsBackButton.setOnClickListener {
            finish()
        }

        binding.activityAlarmSettingsSaveButton.setOnClickListener {
            viewModel.saveAlarmButtonClicked()
            finish()
        }

        binding.activityAlarmSettingsSecondaryTile.setOnClickListener {
            viewModel.alarmNameDialogOpened()
            AlarmNameDialog().show(supportFragmentManager, "AlarmNameDialog")
        }
    }

    private fun handleHourInputText(hour: TimeInputField) {
        binding.activityAlarmSettingsHourTextInput.run {
            setTextColor(resources.getColor(hour.color, null))
            if (text.toString() != hour.time) {
                setText(hour.time)
            }
        }
    }

    private fun handleMinuteInputText(minute: TimeInputField) {
        binding.activityAlarmSettingsMinuteTextInput.run {
            setTextColor(resources.getColor(minute.color, null))
            if (text.toString() != minute.time) {
                setText(minute.time)
            }
        }
    }

    private fun handleDialogState(isDialogOpen: Boolean) {
        if (isDialogOpen) {
            binding.activityAlarmSettingsHourTextInput.isEnabled = false
            binding.activityAlarmSettingsMinuteTextInput.isEnabled = false
        } else {
            binding.activityAlarmSettingsHourTextInput.isEnabled = true
            binding.activityAlarmSettingsMinuteTextInput.isEnabled = true
        }
    }

    private fun handleAlarmNameText(alarmName: String) {
        binding.activityAlarmSettingsName.text = alarmName
    }
}