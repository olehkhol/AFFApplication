package sky.tavrov.affapplication.ui.activities

import android.content.Intent
import android.os.Bundle
import sky.tavrov.affapplication.databinding.ActivityAddressListBinding

class AddressListActivity : BaseActivity() {

    private val binding by lazy { ActivityAddressListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)
            setupActionBar(toolbarAddressListActivity)

            tvAddAddress.setOnClickListener {
                val intent = Intent(this@AddressListActivity, AddEditAddressActivity::class.java)
                startActivity(intent)
            }
        }
    }
}