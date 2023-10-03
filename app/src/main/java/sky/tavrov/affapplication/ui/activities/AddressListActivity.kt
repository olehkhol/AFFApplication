package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.setupActionBarWithNavController
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.databinding.ActivityAddressListBinding

class AddressListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddressListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)

            setupActionBar(toolbarAddressListActivity)
        }
    }

    private fun setupActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
    }
}