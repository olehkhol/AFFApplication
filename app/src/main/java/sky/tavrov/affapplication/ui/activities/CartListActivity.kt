package sky.tavrov.affapplication.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.CartItem
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.ActivityCartListBinding
import sky.tavrov.affapplication.ui.adapters.CartItemsListAdapter
import sky.tavrov.affapplication.ui.utils.Constants

class CartListActivity : BaseActivity() {

    private val binding by lazy { ActivityCartListBinding.inflate(layoutInflater) }
    private lateinit var productsList: ArrayList<Product>
    private lateinit var cartListItems: ArrayList<CartItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)

            setupActionBar(toolbarCartListActivity)

            btnCheckout.setOnClickListener {
                val intent = Intent(
                    this@CartListActivity,
                    AddressListActivity::class.java
                )
                intent.putExtra(Constants.EXTRA_SELECT_ADDRESS, true)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        getProductList()
    }

    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()

        for (product in productsList) {
            for (cartItem in cartList) {
                if (product.product_id == cartItem.product_id) {

                    cartItem.stock_quantity = product.stock_quantity

                    if (product.stock_quantity.toInt() == 0) {
                        cartItem.cart_quantity = product.stock_quantity
                    }
                }
            }
        }

        cartListItems = cartList

        with(binding) {
            if (cartListItems.isNotEmpty()) {
                rvCartItemsList.visibility = View.VISIBLE
                llCheckout.visibility = View.VISIBLE
                tvNoCartItemFound.visibility = View.GONE

                rvCartItemsList.layoutManager = LinearLayoutManager(this@CartListActivity)
                rvCartItemsList.setHasFixedSize(true)
                val cartListAdapter = CartItemsListAdapter(this@CartListActivity, cartList)
                rvCartItemsList.adapter = cartListAdapter
                var subTotal = 0.0
                for (item in cartList) {
                    val availableQuantity = item.cart_quantity.toInt()
                    if (availableQuantity > 0) {
                        val price = item.price.toDouble()
                        val quantity = item.cart_quantity.toInt()

                        subTotal += (price * quantity)
                    }
                }
                tvSubTotal.text = "$$subTotal"
                tvShippingCharge.text = "$10.0"
                if (subTotal > 0) {
                    llCheckout.visibility = View.VISIBLE

                    val total = subTotal + 10
                    tvTotalAmount.text = "$$total"
                }
            } else {
                rvCartItemsList.visibility = View.GONE
                llCheckout.visibility = View.GONE
                tvNoCartItemFound.visibility = View.VISIBLE
            }
        }
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
        hideProgressDialog()
        this.productsList = productsList

        getCartItemsList()
    }

    private fun getProductList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAllProductsList(this@CartListActivity)
    }

    private fun getCartItemsList() {
        FirestoreClass().getCartList(this@CartListActivity)
    }

    fun itemUpdateSuccess() {
        hideProgressDialog()

        getCartItemsList()
    }

    fun itemRemovedSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@CartListActivity,
            resources.getString(R.string.msg_item_removed_successfully),
            Toast.LENGTH_LONG
        ).show()

        getCartItemsList()
    }
}