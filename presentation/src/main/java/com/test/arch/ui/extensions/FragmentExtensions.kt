package com.test.arch.ui.extensions

import android.content.Context
import android.view.View
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory?, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.viewModel(body: T.() -> Unit): T { return viewModel(null, body) }

fun Fragment.showToast(msg: String?, length: Int = Toast.LENGTH_LONG, isBigText: Boolean = true) = msg?.run { appContext?.showToast(this, length, isBigText) }
fun Fragment.showToast(resId: Int?, length: Int = Toast.LENGTH_LONG,  isBigText: Boolean = true) = resId?.run {appContext?.showToast(this, length, isBigText) }
fun Fragment.showSnackBar(view: View?, resId: Int, indefinite: Boolean = false) = appContext?.showSnackBar(view, resId, indefinite)
fun Fragment.showSnackBar(view: View?, msg: String, indefinite: Boolean = false) = appContext?.showSnackBar(view, msg, indefinite)

inline fun <reified T> Fragment.getArgumentsData(key: String): T? {
    if (arguments?.containsKey(key) == true) {
        return arguments?.get(key)?.takeIf { p -> p is T } as T?
    }
    return null
}

inline fun <reified T> FragmentActivity.getExtrasData(key: String): T? {
    if (intent?.extras?.containsKey(key) == true) {
        return intent?.extras?.get(key)?.takeIf { p -> p is T } as T?
    }
    return null
}

val Fragment.appContext: Context? get() = activity?.applicationContext
