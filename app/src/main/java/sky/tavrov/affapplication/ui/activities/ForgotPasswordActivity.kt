package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : BaseActivity() {

    private val binding by lazy { ActivityForgotPasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFullScreenMode()
        setupActionBar()
    }

    private fun setupActionBar() {
        with(binding) {
            setSupportActionBar(toolbarForgotPasswordActivity)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            }
            toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed() }
            btnSubmit.setOnClickListener {
                val email = etEmail.text.toString().trim { it <= ' ' }
                if (email.isEmpty()) {
                    showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                } else {
                    showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->

                            hideProgressDialog()

                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@ForgotPasswordActivity,
                                    resources.getString(R.string.email_sent_success),
                                    Toast.LENGTH_LONG
                                ).show()

                                finish()
                            } else {
                                showErrorSnackBar(task.exception?.message.toString(), true)
                            }
                        }
                }
            }
        }
    }
}