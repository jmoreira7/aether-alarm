package com.example.snoozeloo.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snoozeloo.databinding.ActivityMainBinding
import com.example.snoozeloo.ui.Router
import com.example.snoozeloo.ui.alarmsettings.AlarmSettingsActivity
import com.example.snoozeloo.ui.vo.UiAlarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmAdapter: AlarmAdapter

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        setupAlarmsListRecyclerView()
        setupButtons()
    }

    private fun setupAlarmsListRecyclerView() {
        alarmAdapter = AlarmAdapter(
            ::onAlarmItemClicked,
            ::onAlarmItemSwitchToggled,
            ::onAlarmItemDeleteButtonClicked
        )

        binding.activityMainAlarmList.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = alarmAdapter
        }
    }

    private fun setupButtons() {
        binding.activityMainAddAlarmButton.setOnClickListener {
            launchAlarmSettingsActivity(null, EMPTY_STRING)
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    handleAlarmItems(state.alarmItems)
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

    private fun handleAlarmItems(alarmItems: List<UiAlarm>) {
        setEmptyStateViewsVisibility(alarmItems.isEmpty())
        alarmAdapter.setAlarmItems(alarmItems)
    }

    private fun setEmptyStateViewsVisibility(isEmpty: Boolean) {
        binding.activityMainNoAlarmsYetAlarmLogo.isVisible = isEmpty
        binding.activityMainNoAlarmsYetText.isVisible = isEmpty
    }

    private fun handleRoute(route: Router) {
        when (route) {
            Router.AlarmPermission -> {
                launchPermissionSettings()
            }
        }
    }

    private fun launchPermissionSettings() {
        Log.i(TAG, "Requesting alarm permission")
        Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).also {
            startActivity(it)
        }
    }

    private fun launchAlarmSettingsActivity(alarmId: Int?, alarmName: String) {
        val intent = Intent(this, AlarmSettingsActivity::class.java)
        alarmId?.let { id ->
            intent.putExtra("ALARM_ID", id)
            intent.putExtra("ALARM_NAME", alarmName)
        }
        startActivity(intent)
    }

    private fun onAlarmItemClicked(alarmId: Int, alarmName: String) {
        launchAlarmSettingsActivity(alarmId, alarmName)
    }

    private fun onAlarmItemSwitchToggled(alarmId: Int, isChecked: Boolean) {
        viewModel.alarmItemSwitchToggled(alarmId, isChecked)
    }

    private fun onAlarmItemDeleteButtonClicked(alarmId: Int) {
        viewModel.alarmItemDeleteButtonClicked(alarmId)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val EMPTY_STRING = ""
    }

}