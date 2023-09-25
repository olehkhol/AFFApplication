package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.User
import sky.tavrov.affapplication.databinding.ActivitySettingsBinding
import sky.tavrov.affapplication.ui.utils.GlideLoader

class SettingsActivity : BaseActivity() {

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupActionBar()
    }

    override fun onResume() {
        super.onResume()

        getUserDetails()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarSettingsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarSettingsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getUserDetails(this@SettingsActivity)
    }

    fun userDetailsSuccess(user: User) {
        hideProgressDialog()

        with(binding) {
            GlideLoader(this@SettingsActivity).loadUserPicture(user.image, ivUserPhoto)
            tvName.text = "${user.firstName} ${user.lastName}"
            tvGender.text = user.gender
            tvEmail.text = user.email
            tvMobileNumber.text = "${user.mobile}"
        }
    }
}