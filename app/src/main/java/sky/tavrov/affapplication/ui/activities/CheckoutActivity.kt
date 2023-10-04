package sky.tavrov.affapplication.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Address
import sky.tavrov.affapplication.data.models.CartItem
import sky.tavrov.affapplication.data.models.Order
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.ActivityCheckoutBinding
import sky.tavrov.affapplication.ui.adapters.CartItemsListAdapter
import sky.tavrov.affapplication.ui.utils.Constants

class CheckoutActivity : BaseActivity() {

    private val binding by lazy { ActivityCheckoutBinding.inflate(layoutInflater) }
    private var addressDetails: Address? = null
    private lateinit var productsList: ArrayList<Product>
    private lateinit var cartItemsList: ArrayList<CartItem>
    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0

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

            btnPlaceOrder.setOnClickListener {
                placeAnOrder()
            }

            getProductList()
        }
    }

    private fun placeAnOrder() {
        showProgressDialog(resources.getString(R.string.please_wait))

        if (addressDetails != null) {
            val order = Order(
                    FirestoreClass().getCurrentUserID(),
                    cartItemsList,
                    addressDetails!!,
                    "My order ${System.currentTimeMillis()}",
                    cartItemsList[0].image,
                    mSubTotal.toString(),
                    "10.0",
                    mTotalAmount.toString(),
            )

            FirestoreClass().placeOrder(this@CheckoutActivity, order)
        }
    }

    fun allDetailsUpdatedSuccessfully() {
        hideProgressDialog()

        Toast.makeText(
                this@CheckoutActivity,
                "Your order placed successfully.",
                Toast.LENGTH_SHORT
        ).show()

        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun orderPlacedSuccess() {
        FirestoreClass().updateAllDetails(this, cartItemsList)
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

        with(binding) {
            rvCartListItems.layoutManager = LinearLayoutManager(this@CheckoutActivity)
            rvCartListItems.setHasFixedSize(true)
            rvCartListItems.adapter = CartItemsListAdapter(
                    this@CheckoutActivity,
                    cartItemsList,
                    false
            )

            for (item in cartItemsList) {
                val availableQuantity = item.stock_quantity.toInt()

                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()

                    mSubTotal += (price * quantity)
                }
            }

            tvCheckoutSubTotal.text = "$$mSubTotal"
            tvCheckoutShippingCharge.text = "$10.0"

            if (mSubTotal > 0) {
                llCheckoutPlaceOrder.visibility = View.VISIBLE

                mTotalAmount = mSubTotal + 10.0
                tvCheckoutTotalAmount.text = "$$mTotalAmount"
            } else {
                llCheckoutPlaceOrder.visibility = View.GONE
            }
        }
    }
}