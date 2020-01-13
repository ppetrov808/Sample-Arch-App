package com.test.arch.ui.helper

import android.view.View

class PreventDoubleClickOnClickListener(private val onClickListener: OnClickListener) : View.OnClickListener {

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 1000//milliseconds
    }

    private var lastClickTime: Long = 0

    private fun isSingleClick(): Boolean {
        val clickTime = System.currentTimeMillis()
        val transcureTime = clickTime - lastClickTime
        lastClickTime = clickTime
        return transcureTime > DOUBLE_CLICK_TIME_DELTA
    }

    override fun onClick(v: View) {
        if (isSingleClick()) {
            onClickListener.onClick(v)
        }
    }

    interface OnClickListener {
        fun onClick(v: View)
    }

}