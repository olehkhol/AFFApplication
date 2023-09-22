package sky.tavrov.affapplication.ui.activities

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.databinding.ActivityLoginBinding
import sky.tavrov.affapplication.ui.utils.startActivityFor

@Suppress("DEPRECATION")
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        with(binding) {
            tvRegister.setOnClickListener {
                startActivityFor<RegisterActivity>()
            }
            btnLogin.setOnClickListener {
                loginRegisteredUser()
            }
            tvForgotPassword.setOnClickListener {
                // Handle the forgot password click here.
            }
        }
    }

    private fun loginRegisteredUser() {
        if (validateLoginDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val email = binding.etEmail.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    hideProgressDialog()

                    if (task.isSuccessful) {
                        showErrorSnackBar("You are logged in successfully.", false)
                    } else {
                        showErrorSnackBar(task.exception?.message.toString(), true)
                    }
                }
        }
    }

    private fun validateLoginDetails(): Boolean {
        val checks = listOf(
            Pair(binding.etEmail, R.string.err_msg_enter_email),
            Pair(binding.etPassword, R.string.err_msg_enter_password),
        )

        for ((editText, errorMsgRes) in checks) {
            if (editText.text.toString().trim().isBlank()) {
                showErrorSnackBar(resources.getString(errorMsgRes), true)
                return false
            }
        }

        return true
    }
}