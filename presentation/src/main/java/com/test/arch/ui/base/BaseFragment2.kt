package com.test.arch.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.test.arch.ui.R
import com.test.arch.ui.base.alert_dialog.MyLoadingDialog
import com.test.arch.ui.extensions.hideKeyboard
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment2<VM : BaseViewModel2> : Fragment() {

    protected lateinit var viewModel: VM

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getViewModelFactory(): ViewModelProvider.Factory


    private var dialog: MyLoadingDialog? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId(), container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, getViewModelFactory())[getViewModelClass()]
        lifecycle.addObserver(viewModel)
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            val appCompatActivity = activity as AppCompatActivity
            appCompatActivity.setSupportActionBar(toolbar)
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