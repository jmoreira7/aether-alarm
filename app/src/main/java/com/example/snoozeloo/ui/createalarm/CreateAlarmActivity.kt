package com.example.snoozeloo.ui.createalarm

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.snoozeloo.R
import com.example.snoozeloo.databinding.ActivityCreateAlarmBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CreateAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAlarmBinding

    private val viewModel: CreateAlarmViewModel by viewModels()

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
        }
    }

    private fun setupMinuteTextInputView() {
        binding.activityCreateAlarmMinuteTextInput.run {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    viewModel.minuteInputTextHasFocus()
                }
            }
        }
    }

    private fun setupButtons() {
        binding.activityCreateAlarmBackButton.setOnClickListener {
            finish()
        }
    }

    private fun handleHourInputText(hour: TimeInputField) {
        binding.activityCreateAlarmHourTextInput.run {
            setText(hour.time)
            setTextColor(resources.getColor(hour.color, null))
        }
    }

    private fun handleMinuteInputText(minute: TimeInputField) {
        binding.activityCreateAlarmMinuteTextInput.run {
            setText(minute.time)
            setTextColor(resources.getColor(minute.color, null))
        }
    }
}