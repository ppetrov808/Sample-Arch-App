package com.test.arch.ui.screens.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.textfield.TextInputLayout
import com.test.arch.domain.DomainConstants
import com.test.arch.domain.model.AddressEntity
import com.test.arch.domain.model.SexEntity
import com.test.arch.domain.model.profile.UpdateProfileEntity
import com.test.arch.domain.model.profile.UserProfileEntity
import com.test.arch.ui.Constants
import com.test.arch.ui.Constants.AVATAR_IMAGE_FROM_GALLERY_REQUEST_CODE
import com.test.arch.ui.Constants.EN
import com.test.arch.ui.Constants.GER
import com.test.arch.ui.Constants.MAX_AGE
import com.test.arch.ui.Constants.MIN_AGE
import com.test.arch.ui.R
import com.test.arch.ui.base.BaseFragment2
import com.test.arch.ui.data.ResourceState
import com.test.arch.ui.extensions.showToast
import com.test.arch.ui.helper.PreventDoubleClickOnClickListener
import com.test.arch.ui.screens.auth.ResetErrorTextWatcherWithSelectedText
import com.test.arch.ui.utils.GlideApp
import com.test.arch.ui.utils.StringUtils
import com.test.arch.ui.utils.adapter.CustomViewTargetAdapter
import com.test.arch.ui.utils.adapter.ResetErrorTextWatcher
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.util.*
import javax.inject.Inject

class ProfileFragment : BaseFragment2<ProfileViewModel>(), SelectGenderDialog.SelectGenderListener,
    SelectLanguageDialog.SelectLanguageListener, DatePickerDialog.OnDateSetListener {

    companion object {
        const val TAG = "ProfileFragment"
        private val DOB_FORMAT = DateTimeFormat.forPattern(DomainConstants.DATE_FORMAT)
    }

    @Inject
    lateinit var vmFactory: ProfileViewModelFactory

    private lateinit var profileImageContainer: View
    private lateinit var profileImage: ImageView
    private lateinit var profilePlaceholder: ImageView
    private lateinit var addPhotoText: TextView
    private lateinit var changePhoto: TextView
    private lateinit var removePhoto: ImageView
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var dateOfBirth: EditText
    private lateinit var language: EditText
    private lateinit var gender: EditText
    private lateinit var country: EditText
    private lateinit var city: EditText
    private lateinit var address: EditText
    private lateinit var addressTo: EditText
    private lateinit var index: EditText
    private lateinit var tilFirstName: TextInputLayout
    private lateinit var tilLastName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilDateOfBirth: TextInputLayout

    private var selectGenderDialog: SelectGenderDialog? = null
    private var selectLanguageDialog: SelectLanguageDialog? = null
    private var selectDateOfBirthDialog: DatePickerDialog? = null
    private var imageUrl: String? = null

    override fun layoutId(): Int = R.layout.fragment_profile

    override fun getViewModelClass() = ProfileViewModel::class.java

    override fun getViewModelFactory() = vmFactory

    override fun onGenderSelected(sexEntity: SexEntity, positiveButton: Boolean) {
        if (positiveButton) {
            gender.tag = sexEntity
            gender.setText(getGenderText(sexEntity))
        }
    }

    override fun onLanguageSelected(languageField: String, positiveButton: Boolean) {
        if (positiveButton) {
            language.setText(languageField)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val localDate = LocalDate(year, month + 1, dayOfMonth)
        updateDateOfBirth(localDate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindViews(view)
        setViewListeners()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToLiveData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_apply_profile) {
            viewModel.updateProfile(gatherUpdateProfile())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        selectGenderDialog?.dismissAllowingStateLoss()
        selectLanguageDialog?.dismissAllowingStateLoss()
        selectDateOfBirthDialog?.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.data?.let { uri -> viewModel.uploadProfileImage(uri) }
    }

    private fun bindViews(view: View) {
        profileImageContainer = view.findViewById(R.id.profileImageContainer)
        profileImage = view.findViewById(R.id.profileImage)
        profilePlaceholder = view.findViewById(R.id.profilePlaceholder)
        addPhotoText = view.findViewById(R.id.addPhotoText)
        changePhoto = view.findViewById(R.id.changePhoto)
        removePhoto = view.findViewById(R.id.removePhoto)
        firstName = view.findViewById(R.id.firstName)
        lastName = view.findViewById(R.id.lastName)
        email = view.findViewById(R.id.email)
        dateOfBirth = view.findViewById(R.id.dateOfBirth)
        language = view.findViewById(R.id.language)
        gender = view.findViewById(R.id.gender)
        country = view.findViewById(R.id.country)
        city = view.findViewById(R.id.city)
        address = view.findViewById(R.id.address)
        addressTo = view.findViewById(R.id.address_to)
        index = view.findViewById(R.id.index)
        tilFirstName = view.findViewById(R.id.til_first_name)
        tilLastName = view.findViewById(R.id.til_last_name)
        tilEmail = view.findViewById(R.id.til_email)
        tilDateOfBirth = view.findViewById(R.id.til_date_of_birth)
    }

    private fun setViewListeners() {
        profileImageContainer.setOnClickListener {
            pickImageFromGallery(
                AVATAR_IMAGE_FROM_GALLERY_REQUEST_CODE
            )
        }
        changePhoto.setOnClickListener { pickImageFromGallery(AVATAR_IMAGE_FROM_GALLERY_REQUEST_CODE) }
        removePhoto.setOnClickListener {
            imageUrl = null
            setUpPhotoViews(false)
            profileImage.setImageResource(R.drawable.bg_default_gradient)
        }
        language.setOnClickListener {
            val language = language.text.toString()
            val selectLanguageDialog = SelectLanguageDialog.getInstance(language)
            selectLanguageDialog.selectLanguageListener(this)
            this.selectLanguageDialog = selectLanguageDialog
            selectLanguageDialog.show(childFragmentManager, SelectLanguageDialog.TAG)
        }
        dateOfBirth.setOnClickListener(PreventDoubleClickOnClickListener(object :
            PreventDoubleClickOnClickListener.OnClickListener {
            override fun onClick(v: View) {
                showDatePickerDialog()
            }
        }))
        gender.setOnClickListener {
            val sexEntity = gender.tag as SexEntity?
            val selectGenderDialog = SelectGenderDialog.getInstance(sexEntity)
            selectGenderDialog.selectGenderListener(this)
            this.selectGenderDialog = selectGenderDialog
            selectGenderDialog.show(childFragmentManager, SelectGenderDialog.TAG)
        }
        email.addTextChangedListener(ResetErrorTextWatcherWithSelectedText(tilEmail, email))
        StringUtils.addSpaceFilter(email)
        firstName.addTextChangedListener(ResetErrorTextWatcher(tilFirstName))
        lastName.addTextChangedListener(ResetErrorTextWatcher(tilLastName))
        dateOfBirth.addTextChangedListener(ResetErrorTextWatcher(tilDateOfBirth))
    }

    private fun subscribeToLiveData() {
        viewModel.userProfileLiveData.observe(viewLifecycleOwner, Observer {
            val profile = it.data
            if (ResourceState.SUCCESS == it.status && profile != null) {
                setUpViews(profile)
            } else if (ResourceState.ERROR == it.status) {
                showError(it.error?.message)
            }
        })
        viewModel.updateProfileLiveData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                ResourceState.LOADING -> showProgress(getString(R.string.profile_update))
                ResourceState.SUCCESS -> hideProgress()
                ResourceState.ERROR -> hideProgress().also { showError(resource.error?.message) }
            }
        })
        viewModel.uploadFileLiveData.observe(viewLifecycleOwner, Observer {
            if (ResourceState.LOADING == it.status) {
                showProgress(getString(R.string.picture_upload))
            } else if (ResourceState.SUCCESS == it.status && !it.data.isNullOrEmpty()) {
                loadProfileImage(it.data)
                hideProgress()
            } else {
                hideProgress()
                showError(it.error?.message)
            }
        })
        viewModel.errorFieldLiveData.observe(viewLifecycleOwner, Observer {
            showError(it)
        })
        viewModel.deleteProfileLiveData.observe(viewLifecycleOwner, Observer {
            when {
                ResourceState.LOADING == it.status -> showProgress(getString(R.string.please_wait))
                ResourceState.SUCCESS == it.status -> {
                    hideProgress()
                    showError(getString(R.string.deleting_account_sent_link))
                }
                else -> {
                    hideProgress()
                    showError(it.error?.message)
                }
            }
        })
    }

    private fun showError(fieldFailure: ProfileViewModel.ErrorField) {
        when (fieldFailure) {
            is ProfileViewModel.ErrorField.FirstName -> {
                tilFirstName.error = getString(fieldFailure.resource)
            }
            is ProfileViewModel.ErrorField.LastName -> {
                tilLastName.error = getString(fieldFailure.resource)
            }
            is ProfileViewModel.ErrorField.Email -> {
                val color = ContextCompat.getColor(email.context, R.color.red)
                email.setTextColor(color)
                tilEmail.error = getString(fieldFailure.resource)
            }
            is ProfileViewModel.ErrorField.Birthday -> {
                tilDateOfBirth.error = getString(fieldFailure.resource)
            }
        }
    }

    private fun setUpViews(profile: UserProfileEntity) {
        val hasProfileImage = profile.imgUrl.isNotEmpty()
        setUpPhotoViews(hasProfileImage)
        firstName.setText(profile.firstName)
        lastName.setText(profile.lastName)
        email.setText(profile.email)
        dateOfBirth.setText(profile.birthday)
        when {
            profile.language == null -> language.setText(getString(R.string.english))
            profile.language == EN -> language.setText(getString(R.string.english))
            profile.language == GER -> language.setText(getString(R.string.german))
            else -> language.setText(profile.language)
        }
        gender.tag = profile.sex
        if (profile.sex != null) {
            gender.setText(getGenderText(profile.sex!!))
        } else {
            gender.setText(profile.sex?.name)
        }
        val countryStr = profile.address?.country
        if (countryStr.isNullOrEmpty()) {
            country.setText(R.string.text_germany)
        } else {
            country.setText(countryStr)
        }
        city.setText(profile.address?.city)
        address.setText(profile.address?.street)
        addressTo.setText(profile.address?.to)
        index.setText(profile.address?.zip)
        loadProfileImage(profile.imgUrl)
    }

    private fun setUpPhotoViews(hasProfileImage: Boolean) {
        profilePlaceholder.visibility = if (!hasProfileImage) View.VISIBLE else View.GONE
        addPhotoText.visibility = if (!hasProfileImage) View.VISIBLE else View.GONE
        changePhoto.visibility = if (hasProfileImage) View.VISIBLE else View.GONE
        removePhoto.visibility = if (hasProfileImage) View.VISIBLE else View.GONE
        if (hasProfileImage) {
            profileImageContainer.setOnClickListener(null)
        } else {
            profileImageContainer.setOnClickListener {
                pickImageFromGallery(
                    AVATAR_IMAGE_FROM_GALLERY_REQUEST_CODE
                )
            }
        }
    }

    private fun gatherUpdateProfile(): UpdateProfileEntity {
        val lang = if (language.text.toString().trim() == getString(R.string.german)) {
            GER
        } else {
            EN
        }
        return UpdateProfileEntity(
            AddressEntity(
                city.text.toString().trim(),
                country.text.toString().trim(),
                address.text.toString().trim(),
                addressTo.text.toString().trim(),
                index.text.toString().trim()
            ),
            dateOfBirth.text.toString().trim(),
            email.text.toString().trim(),
            firstName.text.toString().trim(),
            imageUrl ?: "",
            lastName.text.toString().trim(),
            gender.tag as SexEntity?,
            lang
        )
    }

    private fun pickImageFromGallery(requestCode: Int) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = Constants.MEDIA_TYPE_IMAGE
        startActivityForResult(intent, requestCode)
    }

    private fun loadProfileImage(imageUri: String?) {
        this.imageUrl = imageUri
        val customViewTarget = object : CustomViewTargetAdapter<View, Drawable>(profileImage) {

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                profileImage.setImageDrawable(resource)
                setUpPhotoViews(true)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                profileImage.setImageDrawable(errorDrawable)
                setUpPhotoViews(false)
            }
        }
        GlideApp.with(this)
            .load(imageUri)
            .error(android.R.color.darker_gray)
            .into(customViewTarget)
    }

    private fun showError(message: String?) {
        showToast(message, Toast.LENGTH_SHORT)
    }

    private fun getGenderText(sexEntity: SexEntity) = when (sexEntity) {
        SexEntity.MALE -> getString(R.string.gender_male)
        SexEntity.FEMALE -> getString(R.string.gender_female)
        SexEntity.OTHER -> getString(R.string.gender_other)
    }

    private fun updateDateOfBirth(localDate: LocalDate) {
        dateOfBirth.setText(localDate.toString(DomainConstants.DATE_FORMAT))
    }

    private fun showDatePickerDialog() {
        val dateOfBirth = if (dateOfBirth.text.isNullOrEmpty()) {
            LocalDate.now()
        } else {
            LocalDate.parse(dateOfBirth.text.toString(), DOB_FORMAT)
        }
        val datePickerDialog = DatePickerDialog(
            activity,
            R.style.DatePickerStyle,
            this,
            dateOfBirth.year,
            dateOfBirth.monthOfYear - 1,
            dateOfBirth.dayOfMonth
        )
        this.selectDateOfBirthDialog = datePickerDialog
        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR) - MIN_AGE
        val newCalendar = Calendar.getInstance()
        newCalendar.set(year, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.maxDate = newCalendar.timeInMillis

        year = calendar.get(Calendar.YEAR) - MAX_AGE
        newCalendar.set(year, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.minDate = newCalendar.timeInMillis
        datePickerDialog.show()
    }
}