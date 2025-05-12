package com.example.snoozeloo.ui.createalarm

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.snoozeloo.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlarmNameDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alarmTitleView = requireActivity().layoutInflater.inflate(
            R.layout.alarm_name_dialog_title,
            null
        )

        return MaterialAlertDialogBuilder(requireContext())
            .setBackground(resources.getDrawable(R.drawable.alarm_item_background, null))
            .setCustomTitle(alarmTitleView)
            .setView(R.layout.alarm_name_dialog_text_input)
            .setPositiveButton(getString(R.string.alarm_name_dialog_button_text)) { _, _ ->
                // ToDo
            }
            .create()
    }
}