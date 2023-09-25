package sky.tavrov.affapplication.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import sky.tavrov.affapplication.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen") // TODO https://developer.android.com/guide/topics/ui/splash-screen
@Suppress("DEPRECATION")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFullScreenMode()

        Handler().postDelayed(
            {
                startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                finish()
            },
            1500
        )
    }
}