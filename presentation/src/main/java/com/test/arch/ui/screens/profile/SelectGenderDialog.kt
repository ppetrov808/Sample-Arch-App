package com.test.arch.ui.screens.profile

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.test.arch.domain.model.SexEntity
import com.test.arch.ui.R
import java.lang.IllegalStateException

class SelectGenderDialog : DialogFragment() {

    companion object {
        const val TAG = "COM.TEST.ARCH.UI.SCREENS.PROFILE.SELECTGENDERDIALOG"
        private const val SEX_EXTRA = "COM.TEST.ARCH.UI.SCREENS.PROFILE.SELECTGENDERDIALOG.SEX_EXTRA"
        fun getInstance(sexEntity: SexEntity?): SelectGenderDialog {
            val selectGenderDialog = SelectGenderDialog()
            selectGenderDialog.arguments = Bundle().apply { putSerializable(SEX_EXTRA, sexEntity) }
            return selectGenderDialog
        }
    }

    private var selectGenderListener: SelectGenderListener? = null

    private var selectedPosition: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity
        return if (activity != null) {
            AlertDialog.Builder(activity)
                .setTitle(R.string.pick_your_gender)
                .setSingleChoiceItems(R.array.gender_array, getSelectedPosition()) { _, which ->
                    selectedPosition = which
                }
                .setPositiveButton(R.string.button_ok) { _, _ ->
                    selectGenderListener?.onGenderSelected(getGenderFromPosition(selectedPosition), true)
                    dismiss()
                }
                .setNegativeButton(R.string.button_cancel) { _, _ ->
                    selectGenderListener?.onGenderSelected(getGenderFromPosition(selectedPosition), true)
                    dismiss()
                }
                .create()
        } else {
            super.onCreateDialog(savedInstanceState)
        }
    }

    fun selectGenderListener(selectGenderListener: SelectGenderListener) {
        this.selectGenderListener = selectGenderListener
    }

    private fun getSelectedPosition(): Int {
        return when (arguments?.getSerializable(SEX_EXTRA) as SexEntity?) {
            SexEntity.MALE -> 0
            SexEntity.FEMALE -> 1
            SexEntity.OTHER -> 2
            null -> -1
        }
    }

    private fun getGenderFromPosition(position: Int): SexEntity {
        return when (position) {
            0 -> SexEntity.MALE
            1 -> SexEntity.FEMALE
            2 -> SexEntity.OTHER
            else -> throw IllegalStateException()
        }
    }

    interface SelectGenderListener {
        fun onGenderSelected(sexEntity: SexEntity, positiveButton: Boolean)
    }
}