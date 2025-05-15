package com.example.snoozeloo.ui.createalarm

import android.app.Dialog
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.snoozeloo.R
import com.example.snoozeloo.databinding.AlarmNameDialogTextInputBinding
import com.example.snoozeloo.databinding.AlarmNameDialogTitleBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class AlarmNameDialog : DialogFragment() {
    private lateinit var alarmTitleBinding: AlarmNameDialogTitleBinding
    private lateinit var alarmNameInputBinding: AlarmNameDialogTextInputBinding
    private lateinit var alarmNameDialogTextInputField: TextInputEditText
    private lateinit var dialog: Dialog

    private val viewModel: CreateAlarmViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bindViews()

        dialog = MaterialAlertDialogBuilder(requireContext())
            .setBackground(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.alarm_item_background,
                    null
                )
            )
            .setCustomTitle(alarmTitleBinding.root)
            .setView(alarmNameInputBinding.root)
            .setPositiveButton(getString(R.string.alarm_name_dialog_button_text)) { _, _ ->
                handleSaveButtonEvent()
            }
            .create()

        return dialog
    }

    override fun onStart() {
        super.onStart()
        setupViews()
    }

    private fun bindViews() {
        alarmTitleBinding = AlarmNameDialogTitleBinding.inflate(layoutInflater)

        alarmNameInputBinding = AlarmNameDialogTextInputBinding.inflate(layoutInflater).apply {
            alarmNameDialogTextInputField = this.alarmNameTextInputField
        }
    }

    private fun setupViews() {
        dialog.setOnDismissListener {
            viewModel.onAlarmNameDialogDismissed()
        }
        alarmNameDialogTextInputField.requestFocus()
    }

    private fun handleSaveButtonEvent() {
        viewModel.onAlarmNameDialogSaveButtonClicked(alarmNameDialogTextInputField.text.toString())
    }
}