package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sky.tavrov.affapplication.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}