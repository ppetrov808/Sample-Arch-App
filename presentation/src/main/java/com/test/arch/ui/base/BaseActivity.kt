package com.test.arch.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.test.arch.ui.base.alert_dialog.MyLoadingDialog
import com.test.arch.ui.extensions.hideKeyboard
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var dialog: MyLoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
    }

    abstract fun layoutId(): Int

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }


    fun showProgress(message: String?) {
        hideKeyboard()
        dialog.setMessage(message)
        dialog.show(supportFragmentManager, MyLoadingDialog.TAG)

    }

    fun hideProgress() {
        dialog.dismiss()
    }


}
 