package com.test.arch.ui.screens.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.ui.Constants.LOGIN_ENTER_CODE
import com.test.arch.ui.R
import com.test.arch.ui.base.BaseToolbarActivity
import com.test.arch.ui.base.alert_dialog.MyAlertDialogBuilder
import com.test.arch.ui.extensions.hideKeyboard
import com.test.arch.ui.extensions.navigateActivity
import com.test.arch.ui.screens.auth.AuthActivity
import com.test.arch.ui.screens.profile.ProfileFragment
import com.test.arch.ui.utils.GlideApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.panel_left_menu.*

import javax.inject.Inject

class MainActivity : BaseToolbarActivity<MainActivityViewModel>() {

    companion object {
        const val TAG = "MainActivity"
        private const val DELAY_DRAWER_TIME = 260L
    }

    @Inject
    lateinit var vmFactory: MainActivityViewModelFactory

    override fun getViewModelClass(): Class<MainActivityViewModel> =
        MainActivityViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory = vmFactory

    override fun layoutId(): Int = R.layout.activity_main

    private val fm = supportFragmentManager
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var toolbar: Toolbar
    private lateinit var activeFragment: Fragment

    fun getMainActivityViewModel(): MainActivityViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.toolbar)
        setupDrawerLayout()
        setNavigationView()
        setStartFragment()
        with(viewModel) {
            routerLiveData.observe(this@MainActivity, Observer {
                navigate(it)
            })
            profileLiveData.observe(this@MainActivity, Observer {
                initProfile(it)
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initProfile(userProfile: UserProfileEntity?) {
        userProfile?.let {
            with(View.VISIBLE) {
                tv_log_out.visibility = this
                tv_name.visibility = this
                img_avatar.visibility = this
            }
            val firstName = userProfile.firstName
            val lastName = userProfile.lastName
            tv_name.text = "$firstName $lastName"
            if (userProfile.imgUrl.isNotEmpty()) {
                GlideApp.with(this).load(userProfile.imgUrl).into(img_avatar)
            } else {
                img_avatar.setImageResource(R.drawable.ic_avatar_empty)
            }
            img_logo.visibility = View.GONE
        } ?: run {
            with(View.GONE) {
                tv_log_out.visibility = this
                tv_name.visibility = this
                img_avatar.visibility = this
            }
            img_logo.visibility = View.VISIBLE
        }
    }

    private fun setNavigationView() {
        menu_profile.setOnClickListener {
            drawerLayout.postDelayed({ viewModel.onProfileClicked() }, DELAY_DRAWER_TIME)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        tv_log_out.setOnClickListener {
            MyAlertDialogBuilder(this)
                .setTitle(R.string.log_out)
                .setMessage(R.string.log_out_description)
                .setActionButtonClickListener(View.OnClickListener {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    viewModel.onLogOutClicked()
                })
                .build()
                .show()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupDrawerLayout() {
        toggle = object : ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
                val view = currentFocus ?: View(this@MainActivity)
                hideKeyboard(view)
                view.clearFocus()
            }
        }
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    private fun setStartFragment() {
        activeFragment = ProfileFragment()
        fm.beginTransaction()
            .replace(R.id.container, activeFragment, ProfileFragment.TAG)
            .commit()
    }

    private fun navigate(routerNode: MainActivityViewModel.RouterNode) {
        when (routerNode) {
            is MainActivityViewModel.RouterNode.Profile -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                navigateFragment(ProfileFragment.TAG, ::ProfileFragment)
                toolbar.title = getString(R.string.nav_menu_profile)
            }
            is MainActivityViewModel.RouterNode.Login -> {
                navigateActivity(AuthActivity::class.java, routerNode.code)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED && requestCode == LOGIN_ENTER_CODE) {
            finish()
        }
    }

    private fun navigateFragment(
        fragmentTag: String,
        fragmentProvider: () -> Fragment,
        reuseFragment: Boolean = true
    ) {
        var newActiveFragment = if (reuseFragment) fm.findFragmentByTag(fragmentTag) else null
        when {
            newActiveFragment == activeFragment -> return
            newActiveFragment != null -> {
                fm.beginTransaction()
                    .hide(activeFragment)
                    .show(newActiveFragment)
                    .commit()
                activeFragment = newActiveFragment
            }
            else -> {
                newActiveFragment = fragmentProvider()
                fm.beginTransaction()
                    .hide(activeFragment)
                    .add(R.id.container, newActiveFragment, fragmentTag)
                    .commit()
                activeFragment = newActiveFragment
            }
        }
    }
}
