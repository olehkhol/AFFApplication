package sky.tavrov.affapplication.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.models.User
import sky.tavrov.affapplication.databinding.ActivityUserProfileBinding
import sky.tavrov.affapplication.ui.utils.Constants

class UserProfileActivity : BaseActivity() {

    private val binding by lazy { ActivityUserProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userDetails: User = User()
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetails =
                intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        with(binding) {
            setContentView(root)

            etFirstName.isEnabled = false
            etFirstName.setText(userDetails.firstName)

            etLastName.isEnabled = false
            etLastName.setText(userDetails.lastName)

            etEmail.isEnabled = false
            etEmail.setText(userDetails.email)

            ivUserPhoto.setOnClickListener(uploadPhoto())
        }
    }

    private fun uploadPhoto(): View.OnClickListener {
        return View.OnClickListener {
            val permissionToCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }

            if (ContextCompat.checkSelfPermission(this, permissionToCheck) == PackageManager.PERMISSION_GRANTED) {
                showErrorSnackBar("You already have the storage permission.", false)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permissionToCheck),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showErrorSnackBar("The storage permission is granted.", false)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}