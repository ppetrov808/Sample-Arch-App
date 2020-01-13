package com.test.arch.ui.screens.auth

import android.app.Activity
import android.os.Bundle
import com.test.arch.ui.R
import com.test.arch.ui.base.BaseActivity

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)
    }

    override fun layoutId(): Int = R.layout.activity_auth

}

