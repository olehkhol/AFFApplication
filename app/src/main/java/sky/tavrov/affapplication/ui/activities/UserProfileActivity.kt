package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sky.tavrov.affapplication.data.models.User
import sky.tavrov.affapplication.databinding.ActivityUserProfileBinding
import sky.tavrov.affapplication.ui.utils.Constants

class UserProfileActivity : AppCompatActivity() {

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
        }
    }
}