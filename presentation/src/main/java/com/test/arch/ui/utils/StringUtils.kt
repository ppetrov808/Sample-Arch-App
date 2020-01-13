package com.test.arch.ui.utils

import android.text.InputFilter
import android.widget.EditText

object StringUtils { //todo make an extension of String class

    fun containsIgnoreCase(str: String?, searchStr: String?): Boolean {
        if (str == null || searchStr == null) return false

        val length = searchStr.length
        if (length == 0)
            return true

        for (i in str.length - length downTo 0) {
            if (str.regionMatches(i, searchStr, 0, length, ignoreCase = true))
                return true
        }
        return false
    }

    fun formatPrice(amount: Double?): String {
        return String.format("%2.2f", amount)
            .replace(".", ",")
    }

    fun getCurrencySign(currency: String): String {
        return when (currency) {
            "EUR" -> "€"
            "USD" -> "$"
            else -> currency
        }
    }

    fun addSpaceFilter(editText: EditText) {
        //authEditEmail.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
        //    source.toString().filterNot { it.isWhitespace() }
        //})
        // added fix for symbols duplicates on some devices
        editText.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            if (source.contains(" ")) {
                source.toString().filterNot { it.isWhitespace() }
            } else null
        })
    }
}
