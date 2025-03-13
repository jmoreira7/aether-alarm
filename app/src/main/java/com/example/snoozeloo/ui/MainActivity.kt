package com.example.snoozeloo.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.snoozeloo.data.source.platform.AlarmReceiver
import com.example.snoozeloo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")

        installSplashScreen()

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupViewModel()

        // Test
        val intent = Intent(this, AlarmReceiver::class.java)
        viewModel.scheduleAlarm(intent)
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    handleIsRequestingAlarmPermission(it.isRequestingAlarmPermission)
                }
            }
        }
    }

    private fun handleIsRequestingAlarmPermission(isRequestingAlarmPermission: Boolean) {
        if (isRequestingAlarmPermission) {
            Log.i("MainActivity", "Requesting alarm permission")

            viewModel.isRequestingAlarmPermissionHandled()

            Toast.makeText(this, REQUEST_ALARM_PERMISSION_TEXT, Toast.LENGTH_SHORT).show()

            intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }
    }

    companion object {
        private const val REQUEST_ALARM_PERMISSION_TEXT =
            "Please grant permission to schedule exact alarms"
    }

}