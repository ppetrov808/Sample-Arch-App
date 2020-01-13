package com.test.arch.ui.screens.profile

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.test.arch.ui.R

class SelectLanguageDialog : DialogFragment() {

    companion object {

        const val TAG = "COM.TEST.ARCH.UI.SCREENS.PROFILE.SELECTLANGUAGEDIALOG"

        private const val LANGUAGE_EXTRA = "COM.TEST.ARCH.UI.SCREENS.PROFILE.SELECTLANGUAGEDIALOG.LANGUAGE_EXTRA"

        fun getInstance(language: String?): SelectLanguageDialog {
            val selectLanguageDialog = SelectLanguageDialog()
            selectLanguageDialog.arguments = Bundle().apply { putString(LANGUAGE_EXTRA, language) }
            return selectLanguageDialog
        }
    }

    private var selectLanguageListener: SelectLanguageListener? = null

    private var selectedPosition: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity
        return if (activity != null) {
            AlertDialog.Builder(activity)
                .setTitle(R.string.select_your_language)
                .setSingleChoiceItems(R.array.language_array, getSelectedPosition()) { _, which ->
                    selectedPosition = which
                }
                .setPositiveButton(R.string.button_ok) { _, _ ->
                    selectLanguageListener?.onLanguageSelected(getLanguageFromPosition(selectedPosition), true)
                    dismiss()
                }
                .setNegativeButton(R.string.button_cancel) { _, _ ->
                    selectLanguageListener?.onLanguageSelected(getLanguageFromPosition(selectedPosition), true)
                    dismiss()
                }
                .create()
        } else {
            super.onCreateDialog(savedInstanceState)
        }
    }

    fun selectLanguageListener(selectLanguageListener: SelectLanguageListener) {
        this.selectLanguageListener = selectLanguageListener
    }

    private fun getSelectedPosition(): Int {
        val items = context?.resources?.getStringArray(R.array.language_array)
        val language = arguments?.getSerializable(LANGUAGE_EXTRA)
        items?.let {
            for (i in (0 until it.size)) {
                if (language == it[i]) {
                    return i
                }
            }
        }
        return -1
    }

    private fun getLanguageFromPosition(position: Int): String {
        val items = context?.resources?.getStringArray(R.array.language_array)
        items?.let {
            for (i in (0 until it.size)) {
                if (position == i) {
                    return it[i]
                }
            }
        }
        return ""
    }

    interface SelectLanguageListener {
        fun onLanguageSelected(languageField: String, positiveButton: Boolean)
    }
}