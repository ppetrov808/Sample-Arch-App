package com.test.arch.ui.extensions

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import java.util.*

const val NO_ICON_ID = 0

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun View.hide() { this.visibility = View.INVISIBLE }

fun View.setShow(show: Boolean) {
    if (show) {
        visible()
    } else {
        invisible()
    }
}

fun View.setHide(hide: Boolean) {
    if (hide) {
        hide()
    } else {
        visible()
    }
}

fun isLeftToRight() = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_LTR

fun TextView.setStartCompoundDrawable(drawable: Drawable) {
    if (isLeftToRight()) {
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    } else {
        setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }
}

fun TextView.setEndCompoundDrawable(drawable: Drawable) {
    if (isLeftToRight()) {
        setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    } else {
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }
}

fun TextView.setStartCompoundDrawable(@DrawableRes drawableRes: Int) {
    if (isLeftToRight()) {
        setCompoundDrawablesWithIntrinsicBounds(drawableRes, NO_ICON_ID, NO_ICON_ID, NO_ICON_ID)
    } else {
        setCompoundDrawablesWithIntrinsicBounds(NO_ICON_ID, NO_ICON_ID, drawableRes, NO_ICON_ID)
    }
}

fun TextView.setEndCompoundDrawable(@DrawableRes drawableRes: Int) {
    if (isLeftToRight()) {
        setCompoundDrawablesWithIntrinsicBounds(NO_ICON_ID, NO_ICON_ID, drawableRes, NO_ICON_ID)
    } else {
        setCompoundDrawablesWithIntrinsicBounds(drawableRes, NO_ICON_ID, NO_ICON_ID, NO_ICON_ID)
    }
}