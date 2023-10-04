package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Address
import sky.tavrov.affapplication.data.models.CartItem
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.ActivityCheckoutBinding
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

    fun successProductsListFromFireStore(productList: ArrayList<Product>) {
        this.productsList = productsList

        getCartItemsList()
    }

    private fun getCartItemsList() {
        FirestoreClass().getCartList(this@CheckoutActivity)
    }

    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()

        cartItemsList = cartList
    }
}