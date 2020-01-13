package com.test.arch.ui.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import timber.log.Timber

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    view.windowToken?.let {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    } ?: run {
        try {
            if (getKeyboardHeight(inputMethodManager) > 0) {
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (exception: Exception) {
            Timber.e(exception)
        }
    }
}

fun getKeyboardHeight(imm: InputMethodManager): Int = InputMethodManager::class.java.getMethod("getInputMethodWindowVisibleHeight").invoke(imm) as Int

 