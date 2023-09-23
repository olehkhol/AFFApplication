package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sky.tavrov.affapplication.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUserProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}