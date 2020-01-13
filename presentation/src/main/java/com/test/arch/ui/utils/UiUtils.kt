package com.test.arch.ui.utils

import android.content.Context

object UiUtils {
    /**
     * Dp to pixel conversion
     */
    fun dp2px(context: Context, dipValue: Float): Int {
        val m = context.resources.displayMetrics.density
        return (dipValue * m + 0.5f).toInt()
    }
}