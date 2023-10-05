package sky.tavrov.affapplication.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Address
import sky.tavrov.affapplication.databinding.ActivityAddressListBinding
import sky.tavrov.affapplication.ui.adapters.AddressListAdapter
import sky.tavrov.affapplication.ui.utils.Constants
import sky.tavrov.affapplication.ui.utils.SwipeToDeleteCallback
import sky.tavrov.affapplication.ui.utils.SwipeToEditCallback

class AddressListActivity : BaseActivity() {

    private val binding by lazy { ActivityAddressListBinding.inflate(layoutInflater) }
    private var selectAddress: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)
            setupActionBar(toolbarAddressListActivity)

            tvAddAddress.setOnClickListener {
                val intent = Intent(this@AddressListActivity, AddEditAddressActivity::class.java)
                startActivity(intent)
            }
            getAddressList()
            if (intent.hasExtra(Constants.EXTRA_SELECT_ADDRESS)) {
                selectAddress = intent.getBooleanExtra(Constants.EXTRA_SELECT_ADDRESS, false)
            }
            if (selectAddress) {
                tvTitle.text = resources.getString(R.string.title_select_address)
            }

            getAddressList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            getAddressList()
        }
    }

    private fun getAddressList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAddressList(this@AddressListActivity)
    }

    fun successAddressListFromFirestore(addressList: ArrayList<Address>) {
        hideProgressDialog()

        for (i in addressList) {
            Log.i("Name and Address", "${i.name} ::  ${i.address}")
        }
        with(binding) {
            if (addressList.size > 0) {
                rvAddressList.visibility = View.VISIBLE
                tvNoAddressFound.visibility = View.GONE

                rvAddressList.layoutManager = LinearLayoutManager(this@AddressListActivity)
                rvAddressList.setHasFixedSize(true)

                val addressAdapter = AddressListAdapter(
                    this@AddressListActivity,
                    addressList,
                    selectAddress
                )
                rvAddressList.adapter = addressAdapter

                if (!selectAddress) {
                    val editSwipeHandler = object : SwipeToEditCallback(this@AddressListActivity) {

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val adapter = rvAddressList.adapter as AddressListAdapter
                            adapter.notifyEditItem(
                                this@AddressListActivity,
                                viewHolder.adapterPosition
                            )
                        }
                    }
                    val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
                    editItemTouchHelper.attachToRecyclerView(rvAddressList)
                    val deleteSwipeHandler =
                        object : SwipeToDeleteCallback(this@AddressListActivity) {

                            override fun onSwiped(
                                viewHolder: RecyclerView.ViewHolder,
                                direction: Int
                            ) {
                                showProgressDialog(resources.getString(R.string.please_wait))

                                FirestoreClass().deleteAddress(
                                    this@AddressListActivity,
                                    addressList[viewHolder.adapterPosition].id
                                )
                            }
                        }
                    val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
                    deleteItemTouchHelper.attachToRecyclerView(rvAddressList)
                }
            } else {
                rvAddressList.visibility = View.GONE
                tvNoAddressFound.visibility = View.VISIBLE
            }
        }
    }

    fun deleteAddressSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@AddressListActivity,
            resources.getString(R.string.err_your_address_added_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getAddressList()
    }
}