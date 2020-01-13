package com.test.arch.ui.screens.auth.login

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.arch.domain.model.auth.Credentials
import com.test.arch.ui.R
import com.test.arch.ui.base.BaseFragment2
import com.test.arch.ui.extensions.getStateListDrawable
import com.test.arch.ui.extensions.setEndCompoundDrawable
import com.test.arch.ui.extensions.showToast
import com.test.arch.ui.screens.auth.ResetErrorTextWatcherWithSelectedText
import com.test.arch.ui.utils.StringUtils
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@SuppressLint("ResourceType")
class LoginFragment : BaseFragment2<LoginViewModel>() {

    @Inject
    lateinit var vmFactory: LoginViewModelFactory

    override fun layoutId(): Int = R.layout.fragment_login

    override fun getViewModelFactory(): ViewModelProvider.Factory = vmFactory

    override fun getViewModelClass() = LoginViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authLoginBtn?.setOnClickListener { authorize() }
        authSignUpBtn?.setOnClickListener { viewModel.onSignUpClicked() }
        authForgotPasswordBtn?.setOnClickListener { viewModel.onRecoveryPasswordClicked() }
        authEmailEdit?.setEndCompoundDrawable(R.drawable.ic_profile_active, R.drawable.ic_profile_disabled)
        authEmailEdit?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                for (drawable in authEmailEdit.compoundDrawables) {
                    drawable?.colorFilter = PorterDuffColorFilter(
                        ContextCompat.getColor(
                            authEmailEdit.context,
                            R.color.orange_vermilion
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
            } else {
                for (drawable in authEmailEdit.compoundDrawables) {
                    drawable?.colorFilter = PorterDuffColorFilter(
                        ContextCompat.getColor(
                            authEmailEdit.context,
                            R.color.gray_ghost
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }
        authEmailEdit.addTextChangedListener(ResetErrorTextWatcherWithSelectedText(tilLogin, authEmailEdit))
        StringUtils.addSpaceFilter(authEmailEdit)
        authPasswordEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                tilPassword.setPasswordVisibilityToggleTintList(
                    AppCompatResources.getColorStateList(
                        tilPassword.context,
                        R.color.orange_vermilion
                    )
                )
            } else {
                tilPassword.setPasswordVisibilityToggleTintList(
                    AppCompatResources.getColorStateList(
                        tilPassword.context,
                        R.color.gray_ghost
                    )
                )
            }
        }
        authPasswordEdit.addTextChangedListener(ResetErrorTextWatcherWithSelectedText(tilPassword, authPasswordEdit))
    }

    override fun onResume() {
        super.onResume()
        authEmailEdit.text?.clear()
        authPasswordEdit.text?.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        with(viewModel) {
            routeLiveData.observe(this@LoginFragment, Observer {
                route(it)
            })
            errorMessage.observe(this@LoginFragment, Observer { message ->
                context?.let {
                    showToast(message, Toast.LENGTH_LONG)
                }
            })
            errorMessageResource.observe(this@LoginFragment, Observer { resource ->
                context?.let {
                    showToast(getString(resource), Toast.LENGTH_LONG)
                }
            })
            loadingStateLiveData.observe(this@LoginFragment, Observer {
                if (it) {
                    showProgress(getString(R.string.please_wait))
                } else {
                    hideProgress()
                }
            })
            errorField.observe(this@LoginFragment, Observer {
                showError(it)
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showError(fieldFailure: LoginViewModel.ErrorField) {
        val color = ContextCompat.getColor(authEmailEdit.context, android.R.color.holo_red_dark)
        when (fieldFailure) {
            is LoginViewModel.ErrorField.Login -> {
                tilLogin.error = getString(fieldFailure.resource)
                authEmailEdit.setTextColor(color)
                authEmailEdit.requestFocus()
            }
            is LoginViewModel.ErrorField.Password -> {
                tilPassword.error = getString(fieldFailure.resource)
                authPasswordEdit.setTextColor(color)
                authPasswordEdit.requestFocus()
            }
        }
    }

    private fun route(node: LoginViewModel.RouterNode) {
        when (node) {
            LoginViewModel.RouterNode.Main -> activity?.let {
                it.setResult(Activity.RESULT_OK)
                it.finish()
            }
            LoginViewModel.RouterNode.SignUp -> {} //findNavController().navigate(R.id.signUpFragment)
            else -> {} //findNavController().navigate(R.id.passwordRecoveryFragment)
        }
    }

    private fun authorize() {
        val credentials = Credentials(
            authEmailEdit.text.toString(),
            authPasswordEdit.text.toString()
        )
        viewModel.onLoginClicked(credentials)
    }

    private fun EditText.setEndCompoundDrawable(activeDrawableId: Int, disabledDrawableId: Int) {
        val stateListDrawable = context.getStateListDrawable(activeDrawableId, disabledDrawableId)
        setEndCompoundDrawable(stateListDrawable)
    }
}
 