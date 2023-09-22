package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
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
        setSupportActionBar(binding.toolbarForgotPasswordActivity)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed() }
    }
}