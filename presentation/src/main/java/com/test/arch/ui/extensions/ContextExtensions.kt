package com.test.arch.ui.extensions

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView
import android.view.ViewGroup
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

fun Context.getStateListDrawable(activeDrawableId: Int, disabledDrawableId: Int): StateListDrawable {
    val activeDrawable = VectorDrawableCompat.create(resources, activeDrawableId, null)
    val disabledDrawable = VectorDrawableCompat.create(resources, disabledDrawableId, null)
    val stateList = StateListDrawable()
    stateList.addState(intArrayOf(android.R.attr.state_focused, -android.R.attr.state_pressed), activeDrawable)
    stateList.addState(intArrayOf(android.R.attr.state_focused, android.R.attr.state_pressed), disabledDrawable)
    stateList.addState(intArrayOf(-android.R.attr.state_focused, android.R.attr.state_pressed), disabledDrawable)
    stateList.addState(intArrayOf(android.R.attr.state_selected), activeDrawable)
    stateList.addState(intArrayOf(-android.R.attr.state_selected), disabledDrawable)
    return stateList
}

fun Context.showToast(msg: String, length: Int, isBigText: Boolean = true) {
    if (isBigText) {
        val toast = Toast.makeText(this, msg, length)
        val group = toast.view as ViewGroup
        val messageTextView = group.getChildAt(0) as TextView
        messageTextView.textSize = 18f
        toast.show()
    } else {
        Toast.makeText(this, msg, length).show()
    }
}

fun Context.showToast(msgId: Int, length: Int, isBigText: Boolean) {
    val msg = getString(msgId)
    showToast(msg, length, isBigText)
}

fun Context.showSnackBar(view: View?, msg: String, indefinite: Boolean = false) {
    view?.let {
        val length = if (indefinite) {
            Snackbar.LENGTH_INDEFINITE
        } else {
            Snackbar.LENGTH_LONG
        }
        Snackbar.make(it, msg, length).show()
    }
}

fun Context.showSnackBar(view: View?, msgId: Int, indefinite: Boolean = false) {
    val msg = getString(msgId)
    showSnackBar(view, msg, indefinite)
}
