package com.test.arch.ui.base

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.test.arch.ui.R

abstract class BaseToolbarActivity<VM : BaseViewModel2> : BaseActivity() {

    protected lateinit var viewModel: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getViewModelFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        viewModel = ViewModelProviders.of(this, getViewModelFactory())[getViewModelClass()]
        lifecycle.addObserver(viewModel)
    }

    protected fun setupActionBar() {
        setupActionBar(findViewById(R.id.toolbar))
    }

    protected open fun setupActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val bar = supportActionBar
        if (bar != null) {
            with(bar) {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setDisplayShowTitleEnabled(false)
            }
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
    }

}
