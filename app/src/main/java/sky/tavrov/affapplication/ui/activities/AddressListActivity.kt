package sky.tavrov.affapplication.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Address
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

    override fun onResume() {
        super.onResume()

        getAddressList()
    }

    private fun getAddressList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAddressList(this@AddressListActivity)
    }

    fun successAddressListFromFirestore(addressList: ArrayList<Address>) {
        hideProgressDialog()

        for (i in addressList) {
            Log.i("Name and Address", "${i.name} :: ${i.address}")
        }
    }
}