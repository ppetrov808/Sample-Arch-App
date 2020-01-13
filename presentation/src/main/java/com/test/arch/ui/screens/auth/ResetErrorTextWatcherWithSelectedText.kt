package com.test.arch.ui.screens.auth

import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.test.arch.ui.R
import com.test.arch.ui.utils.adapter.TextWatcherAdapter

class ResetErrorTextWatcherWithSelectedText(
    private val textInputLayout: TextInputLayout,
    private val editText: EditText
) : TextWatcherAdapter {

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.trim().isNotEmpty()) {
            textInputLayout.error = null
            val color = ContextCompat.getColor(editText.context, R.color.dark_gray_tundora)
            editText.setTextColor(color)
        }
    }
}