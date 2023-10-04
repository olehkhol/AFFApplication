package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Address
import sky.tavrov.affapplication.data.models.CartItem
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.ActivityCheckoutBinding
import sky.tavrov.affapplication.ui.adapters.CartItemsListAdapter
import sky.tavrov.affapplication.ui.utils.Constants

class CheckoutActivity : BaseActivity() {

    private val binding by lazy { ActivityCheckoutBinding.inflate(layoutInflater) }
    private var addressDetails: Address? = null
    private lateinit var productsList: ArrayList<Product>
    private lateinit var cartItemsList: ArrayList<CartItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)
            setupActionBar(toolbarCheckoutActivity)

            if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
                addressDetails = intent.getParcelableExtra(Constants.EXTRA_SELECTED_ADDRESS)
            }

            if (addressDetails != null) {
                tvCheckoutAddressType.text = addressDetails?.type
                tvCheckoutFullName.text = addressDetails?.type
                tvCheckoutAddress.text = "${addressDetails!!.type}, ${addressDetails!!.zipCode}"
                tvCheckoutAdditionalNote.text = addressDetails?.additionalNote

                if (addressDetails?.otherDetails!!.isNotEmpty()) {
                    tvCheckoutOtherDetails.text = addressDetails?.otherDetails
                }
                tvCheckoutMobileNumber.text = addressDetails?.mobileNumber
            }
        }
    }

    private fun getProductList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAllProductsList(this@CheckoutActivity)
    }

    fun successProductsListFromFireStore(list: ArrayList<Product>) {
        this.productsList = list

        getCartItemsList()
    }

    private fun getCartItemsList() {
        FirestoreClass().getCartList(this@CheckoutActivity)
    }

    fun successCartItemsList(list: ArrayList<CartItem>) {
        hideProgressDialog()
        for (product in productsList) {
            for (cartItem in list) {
                if (product.product_id == cartItem.product_id) {
                    cartItem.stock_quantity = product.stock_quantity
                }
            }
        }
        cartItemsList = list

        with(binding.rvCartListItems) {
            layoutManager = LinearLayoutManager(this@CheckoutActivity)
            setHasFixedSize(true)
            adapter = CartItemsListAdapter(
                this@CheckoutActivity,
                cartItemsList,
                false
            )
        }
    }
}