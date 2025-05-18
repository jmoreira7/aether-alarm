package com.jmoreira7.aetheralarm.ui.alarmsettings

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jmoreira7.aetheralarm.databinding.ActivityAlarmSettingsBinding
import kotlinx.coroutines.launch

class AlarmSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmSettingsBinding

    private val viewModel: AlarmSettingsViewModel by viewModels {
        AlarmSettingsViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAlarmSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExtras()
        setupViews()
        setupViewModel()
    }

    private fun getExtras() {
        intent.run {
            getIntExtra(ALARM_ID_EXTRA, INVALID_VALUE)
                .takeIf { it != INVALID_VALUE }?.let { alarmId ->
                    viewModel.alarmId = alarmId
                }


            getStringExtra(ALARM_NAME_EXTRA)?.let { alarmName ->
                viewModel.setAlarmName(alarmName)
            }

            getStringExtra(ALARM_HOUR_EXTRA)
                ?.takeIf { it.isNotBlank() }?.let { alarmHour ->
                    viewModel.setAlarmHour(alarmHour)
                }

            getStringExtra(ALARM_MINUTE_EXTRA)
                ?.takeIf { it.isNotBlank() }?.let { alarmMinute ->
                    viewModel.setAlarmMinute(alarmMinute)
                }
        }
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
            showAlarmNameDialog(viewModel.alarmName)
        }
    }

    private fun showAlarmNameDialog(alarmName: String) {
        val args = Bundle().apply {
            putString(ALARM_NAME_KEY, alarmName)
        }
        AlarmNameDialog().run {
            arguments = args
            show(supportFragmentManager, "AlarmNameDialog")
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

    companion object {
        private const val INVALID_VALUE = -1
        private const val ALARM_ID_EXTRA = "ALARM_ID"
        private const val ALARM_NAME_EXTRA = "ALARM_NAME"
        private const val ALARM_NAME_KEY = "ALARM_NAME"
        private const val ALARM_HOUR_EXTRA = "ALARM_HOUR"
        private const val ALARM_MINUTE_EXTRA = "ALARM_MINUTE"
    }
}