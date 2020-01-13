package com.test.arch.ui.base.alert_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import com.test.arch.ui.R
import javax.inject.Inject


class MyLoadingDialog @Inject constructor() : DialogFragment() {

    companion object {
        const val TAG = "MyLoadingDialog"
    }

    private var message: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.view_loading, null)
        val builder = AlertDialog.Builder(view.context)
        val textView: AppCompatTextView = view.findViewById(R.id.tv_message)
        message?.let {
            textView.text = it
        }
        builder.setView(view)
        return builder.create()
    }


    fun setMessage(message: String?) {
        this.message = message
    }

}