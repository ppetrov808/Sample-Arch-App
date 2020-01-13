package com.test.arch.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

abstract class BaseSimpleActivity<VM : BaseViewModel2> : BaseActivity() {

    protected lateinit var viewModel: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getViewModelFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, getViewModelFactory())[getViewModelClass()]
        lifecycle.addObserver(viewModel)
    }
}
