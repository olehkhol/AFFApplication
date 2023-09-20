package sky.tavrov.affapplication.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.databinding.ActivityRegisterBinding

@Suppress("DEPRECATION")
class RegisterActivity : BaseActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFullScreenMode()
        setupActionBar()
        binding.tvLogin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            ); finish()
        }
        binding.btnRegister.setOnClickListener { validateRegisterDetails() }
    }

    private fun setFullScreenMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
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

            else -> {
                showErrorSnackBar(resources.getString(R.string.register_success), false)
                true
            }
        }
    }
}