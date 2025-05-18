package com.jmoreira7.aetheralarm.ui.alarmsettings

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.jmoreira7.aetheralarm.R
import com.jmoreira7.aetheralarm.databinding.AlarmNameDialogTextInputBinding
import com.jmoreira7.aetheralarm.databinding.AlarmNameDialogTitleBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class AlarmNameDialog : DialogFragment() {
    private lateinit var alarmTitleBinding: AlarmNameDialogTitleBinding
    private lateinit var alarmNameInputBinding: AlarmNameDialogTextInputBinding
    private lateinit var alarmNameDialogTextInputField: TextInputEditText
    private lateinit var dialog: Dialog

    private val viewModel: AlarmSettingsViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bindViews()
        getArgs()

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

    private fun getArgs() {
        arguments?.getString(ALARM_NAME_EXTRA)?.let { alarmName ->
            alarmNameDialogTextInputField.text =
                Editable.Factory.getInstance().newEditable(alarmName)
        }
    }

    private fun setupViews() {
        dialog.setOnDismissListener {
            viewModel.alarmNameDialogDismissed()
        }
        alarmNameDialogTextInputField.requestFocus()
    }

    private fun handleSaveButtonEvent() {
        viewModel.alarmNameDialogSaveButtonClicked(alarmNameDialogTextInputField.text.toString())
    }

    companion object {
        const val ALARM_NAME_EXTRA = "ALARM_NAME"
    }
}