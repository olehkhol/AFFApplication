package sky.tavrov.affapplication.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.User
import sky.tavrov.affapplication.databinding.ActivityUserProfileBinding
import sky.tavrov.affapplication.ui.utils.Constants
import sky.tavrov.affapplication.ui.utils.Constants.showImageChooser
import sky.tavrov.affapplication.ui.utils.GlideLoader
import sky.tavrov.affapplication.ui.utils.startActivityFor
import sky.tavrov.affapplication.ui.utils.trimmedText
import java.io.IOException

class UserProfileActivity : BaseActivity() {

    private val binding by lazy { ActivityUserProfileBinding.inflate(layoutInflater) }
    private lateinit var userDetails: User
    private var selectedImageFileUri: Uri? = null
    private var userProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)

            if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
                userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
            }

            if (userDetails.profileCompleted == 0) {
                tvTitle.text = resources.getString(R.string.title_complete_profile)
                etFirstName.isEnabled = false
                etFirstName.setText(userDetails.firstName)
                etLastName.isEnabled = false
                etLastName.setText(userDetails.lastName)
                etEmail.isEnabled = false
                etEmail.setText(userDetails.email)
            } else {
                setupActionBar(toolbarUserProfileActivity)

                tvTitle.text = resources.getString(R.string.title_edit_profile)
                GlideLoader(this@UserProfileActivity).loadUserPicture(
                    userDetails.image,
                    ivUserPhoto
                )
                etFirstName.setText(userDetails.firstName)
                etLastName.setText(userDetails.lastName)
                etEmail.isEnabled = false
                etEmail.setText(userDetails.email)
                if (userDetails.mobile != 0L) {
                    etMobileNumber.setText(userDetails.mobile.toString())
                }
                if (userDetails.gender == Constants.MALE) {
                    rbMale.isChecked = true
                } else {
                    rbFemale.isChecked = true
                }
            }

            btnSubmit.setOnClickListener {
                if (validateUserProfileDetails()) {
                    showProgressDialog(resources.getString(R.string.please_wait))

                    if (selectedImageFileUri != null) {
                        FirestoreClass().uploadImageToCloudStorage(
                            this@UserProfileActivity,
                            selectedImageFileUri
                        )
                    } else {
                        updateUserProfileDetails()
                    }
                }
            }

            ivUserPhoto.setOnClickListener(uploadPhoto())
        }
    }

    private fun updateUserProfileDetails() {
        val userHashMap = HashMap<String, Any>()
        val email = binding.etEmail.trimmedText()
        val firstName = binding.etFirstName.trimmedText()
        val lastName = binding.etLastName.trimmedText()
        val mobileNumber = binding.etMobileNumber.trimmedText()
        val gender = if (binding.rbMale.isChecked) Constants.MALE else Constants.FEMALE

        if (firstName != userDetails.firstName) {
            userHashMap[Constants.FIRST_NAME] = firstName
        }
        if (lastName != userDetails.lastName) {
            userHashMap[Constants.LAST_NAME] = lastName
        }
        if (userProfileImageURL.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = userProfileImageURL
        }
        if (mobileNumber.isNotEmpty() && mobileNumber != userDetails.mobile.toString()) {
            userHashMap[Constants.MOBILE] = mobileNumber.toLong()
        }
        if (gender.isNotEmpty() && gender != userDetails.gender) {
            userHashMap[Constants.GENDER] = gender
        }
        userHashMap[Constants.COMPLETE_PROFILE] = 1

        FirestoreClass().updateUserProfile(this@UserProfileActivity, userHashMap)
    }

    private fun uploadPhoto(): View.OnClickListener {
        return View.OnClickListener {
            val permissionToCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }

            if (ContextCompat.checkSelfPermission(
                    this,
                    permissionToCheck
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser(this@UserProfileActivity)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permissionToCheck),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    fun userProfileUpdateSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@UserProfileActivity,
            resources.getString(R.string.msg_profile_update_success),
            Toast.LENGTH_LONG
        ).show()

        startActivityFor<DashboardActivity>()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser(this@UserProfileActivity)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        selectedImageFileUri = data.data
                        selectedImageFileUri?.let {
                            GlideLoader(this).loadUserPicture(
                                it,
                                binding.ivUserPhoto
                            )
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Request cancelled", "Image selection cancelled")
        }
    }

    private fun validateUserProfileDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etMobileNumber.trimmedText()) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }

            else -> {
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL: String) {
        userProfileImageURL = imageURL
        updateUserProfileDetails()
    }
}