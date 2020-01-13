package com.test.arch.ui.utils.adapter

import com.google.android.material.textfield.TextInputLayout

class ResetErrorTextWatcher(private val textInputLayout: TextInputLayout) : TextWatcherAdapter {

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.trim().isNotEmpty()) {
            textInputLayout.error = null
        }
    }
}
