package com.test.arch.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.test.arch.ui.base.alert_dialog.MyLoadingDialog
import com.test.arch.ui.extensions.hideKeyboard
import com.test.arch.ui.extensions.setupActionBar
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    private var dialog: MyLoadingDialog? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId(), container, false)

    abstract fun layoutId(): Int

    fun setupActionBar(toolbar: Toolbar) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setupActionBar(toolbar)
        }
    }

    fun setTitle(titleId: Int) {
        val titleStr = getString(titleId)
        setTitle(titleStr)
    }

    fun setTitle(title: String) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = title
        }
    }

    fun showProgress(message: String?, cancellable: Boolean = false) {
        hideKeyboard()
        if (dialog == null) {
            dialog = MyLoadingDialog()
            fragmentManager?.let {
                dialog?.setMessage(message)
                dialog?.show(it, MyLoadingDialog.TAG)
            }
        }
        dialog?.isCancelable = cancellable
    }

    fun hideProgress() {
        dialog?.dismiss()
        dialog = null
    }
}