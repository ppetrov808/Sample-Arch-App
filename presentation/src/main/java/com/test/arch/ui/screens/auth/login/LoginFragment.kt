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
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.test.arch.domain.model.auth.Credentials
import com.test.arch.ui.R
import com.test.arch.ui.base.BaseFragment2
import com.test.arch.ui.extensions.getStateListDrawable
import com.test.arch.ui.extensions.setEndCompoundDrawable
import com.test.arch.ui.extensions.showToast
import com.test.arch.ui.screens.auth.ResetErrorTextWatcherWithSelectedText
import com.test.arch.ui.utils.StringUtils
import javax.inject.Inject

@SuppressLint("ResourceType")
class LoginFragment : BaseFragment2<LoginViewModel>() {

    //region base

    @Inject
    lateinit var vmFactory: LoginViewModelFactory

    override fun layoutId(): Int = R.layout.fragment_login

    override fun getViewModelFactory(): ViewModelProvider.Factory = vmFactory

    override fun getViewModelClass() = LoginViewModel::class.java

    //endregion

    //region fields

    lateinit var authLogin: AppCompatEditText

    lateinit var authPassword: AppCompatEditText

    lateinit var btnLogin: AppCompatButton

    lateinit var tvSignUp: AppCompatTextView

    lateinit var tvForgotPassword: AppCompatTextView

    lateinit var tilPassword: TextInputLayout

    lateinit var tilLogin: TextInputLayout

    //endregion

    //region live

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authLogin = view.findViewById(R.id.auth_edit_email)
        authPassword = view.findViewById(R.id.auth_edit_password)
        btnLogin = view.findViewById(R.id.auth_button_login)
        tvSignUp = view.findViewById(R.id.auth_button_sign_up)
        tvForgotPassword = view.findViewById(R.id.auth_button_forgot_password)
        tilPassword = view.findViewById(R.id.til_password)
        tilLogin = view.findViewById(R.id.til_login)
        btnLogin.setOnClickListener { authorize() }
        tvSignUp.setOnClickListener { viewModel.onSignUpClicked() }
        tvForgotPassword.setOnClickListener { viewModel.onRecoveryPasswordClicked() }
        authLogin.setEndCompoundDrawable(R.drawable.ic_profile_active, R.drawable.ic_profile_disabled)
        authLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                for (drawable in authLogin.compoundDrawables) {
                    drawable?.colorFilter = PorterDuffColorFilter(
                        ContextCompat.getColor(
                            authLogin.context,
                            R.color.orange_vermilion
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
            } else {
                for (drawable in authLogin.compoundDrawables) {
                    drawable?.colorFilter = PorterDuffColorFilter(
                        ContextCompat.getColor(
                            authLogin.context,
                            R.color.gray_ghost
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }
        authLogin.addTextChangedListener(ResetErrorTextWatcherWithSelectedText(tilLogin, authLogin))
        StringUtils.addSpaceFilter(authLogin)
        authPassword.setOnFocusChangeListener { _, hasFocus ->
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
        authPassword.addTextChangedListener(ResetErrorTextWatcherWithSelectedText(tilPassword, authPassword))
    }

    override fun onResume() {
        super.onResume()
        authLogin.text?.clear()
        authPassword.text?.clear()
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

    //endregion

    private fun showError(fieldFailure: LoginViewModel.ErrorField) {
        val color = ContextCompat.getColor(authLogin.context, android.R.color.holo_red_dark)
        when (fieldFailure) {
            is LoginViewModel.ErrorField.Login -> {
                tilLogin.error = getString(fieldFailure.resource)
                authLogin.setTextColor(color)
                authLogin.requestFocus()
            }
            is LoginViewModel.ErrorField.Password -> {
                tilPassword.error = getString(fieldFailure.resource)
                authPassword.setTextColor(color)
                authPassword.requestFocus()
            }
        }
    }

    //region navigate

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

    //endregion

    //region own methods

    private fun authorize() {
        val credentials = Credentials(
            authLogin.text.toString(),
            authPassword.text.toString()
        )
        viewModel.onLoginClicked(credentials)
    }

    //endregion

    private fun EditText.setEndCompoundDrawable(activeDrawableId: Int, disabledDrawableId: Int) {
        val stateListDrawable = context.getStateListDrawable(activeDrawableId, disabledDrawableId)
        setEndCompoundDrawable(stateListDrawable)
    }
}
 