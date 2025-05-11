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
import com.example.snoozeloo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    // ToDo
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate) {
                    viewModel.router.collect { route ->
                        Log.d(TAG, "Route: $route")
                        handleRoute(route)
                    }
                }
            }
        }
    }

    private fun handleRoute(route: Router) {
        when (route) {
            Router.AlarmPermission -> {
                notifyRequestForPermission()
                launchPermissionSettings()
            }
        }
    }

    private fun notifyRequestForPermission() {
        lifecycleScope.launch {
            delay(500L)
            Toast.makeText(
                this@MainActivity,
                REQUEST_ALARM_PERMISSION_TEXT,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun launchPermissionSettings() {
        Log.i(TAG, "Requesting alarm permission")
        Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).also {
            startActivity(it)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_ALARM_PERMISSION_TEXT =
            "Please grant permission to schedule exact alarms"
    }

}