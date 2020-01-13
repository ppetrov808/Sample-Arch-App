package com.test.arch.ui.extensions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.test.arch.ui.R
import com.test.arch.ui.base.BaseActivity
import com.test.arch.ui.base.HostFragmentListener
import com.test.arch.ui.base.OnBackPressedListener

inline fun <reified T : ViewModel> FragmentActivity.viewModel(factory: ViewModelProvider.Factory?, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> FragmentActivity.viewModel(body: T.() -> Unit): T { return viewModel(null, body) }

fun FragmentActivity.isFragmentBackPressed(manager: FragmentManager?): Boolean? {
     val fragmentList = manager?.fragments
     return fragmentList?.map {
         val res = when (it) {
             is NavHostFragment,
             is HostFragmentListener -> isFragmentBackPressed(it.childFragmentManager)
             is OnBackPressedListener -> it.onBackPressed()
             else -> true
         }
         res ?: true
     }?.min()
}

fun AppCompatActivity.setupActionBar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    val bar = supportActionBar
    if (bar != null) {
        with(bar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
    }
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
}

fun <T : BaseActivity> BaseActivity.navigateActivity(clazz: Class<T>, requestCode: Int? = null, extras: Bundle? = null) {
    val intent = Intent(this, clazz)
    extras?.let { intent.putExtras(it) }
    if (requestCode != null) {
        startActivityForResult(intent, requestCode)
    } else {
        startActivity(intent)
    }
}
