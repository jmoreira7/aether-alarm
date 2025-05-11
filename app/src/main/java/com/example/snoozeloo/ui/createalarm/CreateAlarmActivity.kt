package com.example.snoozeloo.ui.createalarm

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.snoozeloo.R
import com.example.snoozeloo.databinding.ActivityCreateAlarmBinding

class CreateAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        setupHourTextInputView()
        setupMinuteTextInputView()
    }

    private fun setupHourTextInputView() {
        binding.activityCreateAlarmHourTextInput.run {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setTextColor(resources.getColor(R.color.dodger_blue, null))
                    setText(EMPTY_STRING)
                }
            }
        }
    }

    private fun setupMinuteTextInputView() {
        binding.activityCreateAlarmMinuteTextInput.run {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setTextColor(resources.getColor(R.color.dodger_blue, null))
                    setText(EMPTY_STRING)
                }
            }
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}