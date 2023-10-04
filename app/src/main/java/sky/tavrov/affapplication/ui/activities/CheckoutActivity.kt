package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.databinding.ActivityCheckoutBinding

class CheckoutActivity : BaseActivity() {

    private val binding by lazy { ActivityCheckoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)

            setupActionBar(toolbarCheckoutActivity)
        }
    }
}