package com.jmoreira7.aetheralarm.ui.alarmtrigger

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jmoreira7.aetheralarm.databinding.ActivityAlarmTriggerBinding
import kotlinx.coroutines.launch

class AlarmTriggerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmTriggerBinding

    private val viewModel: AlarmTriggerViewModel by viewModels {
        AlarmTriggerViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAlarmTriggerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        getExtras()
        setupViews()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    handleAlarmTime(state.alarmTime)
                    handleAlarmName(state.alarmName)
                }
            }
        }
    }

    private fun getExtras() {
        intent.getIntExtra(ALARM_ID_EXTRA, -1).let { alarmId ->
            Log.d(TAG, "Received intent with alarm id extra: $alarmId")
            viewModel.setup(alarmId)
        }
    }

    private fun setupViews() {
        binding.activityAlarmTriggerTurnOffButton.setOnClickListener {
            viewModel.triggerOffButtonClicked()
            finish()
        }
    }

    private fun handleAlarmTime(alarmTime: String) {
        binding.activityAlarmTriggerTimeText.text = alarmTime
    }

    private fun handleAlarmName(alarmName: String) {
        binding.activityAlarmTriggerAlarmNameText.text = alarmName
    }

    companion object {
        private const val TAG = "AlarmTriggerActivity"
        const val ALARM_ID_EXTRA = "ALARM_ID"
    }
}