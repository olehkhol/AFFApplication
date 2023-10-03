package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import sky.tavrov.affapplication.databinding.ActivityAddEditAddressBinding

class AddEditAddressActivity : BaseActivity() {

    private val binding by lazy { ActivityAddEditAddressBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)

            setupActionBar(toolbarAddEditAddressActivity)
        }
    }
}