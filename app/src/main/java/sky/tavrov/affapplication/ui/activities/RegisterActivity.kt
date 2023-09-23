package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.User
import sky.tavrov.affapplication.databinding.ActivityRegisterBinding
import sky.tavrov.affapplication.ui.utils.trimWhitespace

class RegisterActivity : BaseActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFullScreenMode()
        setupActionBar()
        with(binding) {
            tvLogin.setOnClickListener {
                onBackPressed()
            }
            btnRegister.setOnClickListener {
                registerUser()
            }
        }
    }

    private fun registerUser() {

        if (validateRegisterDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = binding.etEmail.text.toString().trimWhitespace()
            val password: String = binding.etPassword.text.toString().trimWhitespace()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val user = User(
                            firebaseUser.uid,
                            binding.etFirstName.text.toString().trimWhitespace(),
                            binding.etLastName.text.toString().trimWhitespace(),
                            binding.etEmail.text.toString().trimWhitespace()
                        )

                        FirestoreClass().registerUser(this@RegisterActivity, user)
                    } else {

                        hideProgressDialog()

                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarRegisterActivity)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        binding.toolbarRegisterActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegisterDetails(): Boolean {
        val checks = listOf(
            Pair(binding.etFirstName, R.string.err_msg_enter_first_name),
            Pair(binding.etLastName, R.string.err_msg_enter_last_name),
            Pair(binding.etEmail, R.string.err_msg_enter_email),
            Pair(binding.etPassword, R.string.err_msg_enter_password),
            Pair(binding.etConfirmPassword, R.string.err_msg_enter_confirm_password)
        )

        for ((editText, errorMsgRes) in checks) {
            if (editText.text.toString().trim().isBlank()) {
                showErrorSnackBar(resources.getString(errorMsgRes), true)
                return false
            }
        }

        val trimmedPass = binding.etPassword.text.toString().trim()
        val trimmedConfirmPass = binding.etConfirmPassword.text.toString().trim()

        return when {
            trimmedPass != trimmedConfirmPass -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }

            !binding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }

            else -> true
        }
    }

    fun userRegistrationSuccess() {

        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()


        FirebaseAuth.getInstance().signOut()
        finish()
    }
}