package com.test.arch.ui.base.alert_dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.test.arch.ui.R

class MyAlertDialogBuilder constructor(private val context: Context) {


    private var title: String? = null
    private var message: String? = null
    private var actionButtonName: String? = null
    private var cancelButtonName: String? = null
    private var actionButtonClickListener: View.OnClickListener? = null
    private var cancelButtonClickListener: View.OnClickListener? = null


    fun setTitle(@StringRes titleId: Int): MyAlertDialogBuilder {
        title = context.getText(titleId).toString()
        return this
    }

    fun setTitle(title: String): MyAlertDialogBuilder {
        this.title = title
        return this
    }

    fun setMessage(@StringRes messageId: Int): MyAlertDialogBuilder {
        message = context.getText(messageId).toString()
        return this
    }

    fun setMessage(message: String): MyAlertDialogBuilder {
        this.message = message
        return this
    }

    fun setActionButtonName(@StringRes actionButtonNameId: Int): MyAlertDialogBuilder {
        actionButtonName = context.getText(actionButtonNameId).toString()
        return this
    }

    fun setActionButtonName(actionButtonName: String): MyAlertDialogBuilder {
        this.actionButtonName = actionButtonName
        return this
    }

    fun setCancelButtonName(@StringRes cancelButtonNameId: Int): MyAlertDialogBuilder {
        cancelButtonName = context.getText(cancelButtonNameId).toString()
        return this
    }

    fun setCancelButtonName(cancelButtonName: String): MyAlertDialogBuilder {
        this.cancelButtonName = cancelButtonName
        return this
    }

    fun setActionButtonClickListener(
        actionButtonClickListener: View.OnClickListener
    ): MyAlertDialogBuilder {
        this.actionButtonClickListener = actionButtonClickListener
        return this
    }

    fun setCancelButtonClickListener(
        cancelButtonClickListener: View.OnClickListener
    ): MyAlertDialogBuilder {
        this.cancelButtonClickListener = cancelButtonClickListener
        return this
    }

    @SuppressLint("InflateParams")
    fun build(): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.view_modal_dialog, null)
        val alertDialog = AlertDialog
            .Builder(context)
            .setView(view)
            .setCancelable(false)
            .create()
        val tvTitle = view.findViewById(R.id.tv_title) as AppCompatTextView
        val tvDescription = view.findViewById(R.id.tv_description) as AppCompatTextView
        val btnAction = view.findViewById(R.id.tv_action) as AppCompatTextView
        val btnCancel = view.findViewById(R.id.tv_cancel) as AppCompatTextView
        if (TextUtils.isEmpty(title)) {
            tvTitle.setText(R.string.error)
        } else {
            tvTitle.text = title
        }
        if (TextUtils.isEmpty(message)) {
            tvDescription.visibility = View.GONE
        } else {
            tvDescription.text = message
        }
        actionButtonClickListener?.let {
            if (TextUtils.isEmpty(actionButtonName)) {
                btnAction.setText(R.string.yes)
            } else {
                btnAction.text = actionButtonName
            }
            btnAction.setOnClickListener { v: View ->
                alertDialog.dismiss()
                actionButtonClickListener?.onClick(v)
            }
            if (TextUtils.isEmpty(cancelButtonName)) {
                btnCancel.setText(R.string.cancel)
            } else {
                btnCancel.text = cancelButtonName
            }
        } ?: run {
            btnAction.visibility = View.GONE
            if (TextUtils.isEmpty(cancelButtonName)) {
                btnCancel.setText(R.string.ok)
            } else {
                btnCancel.text = cancelButtonName
            }
        }
        btnCancel.setOnClickListener { v: View ->
            alertDialog.cancel()
            cancelButtonClickListener?.onClick(v)

        }
        return alertDialog
    }

}