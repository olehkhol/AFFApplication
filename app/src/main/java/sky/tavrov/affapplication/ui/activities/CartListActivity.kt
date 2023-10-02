package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.CartItem
import sky.tavrov.affapplication.databinding.ActivityCartListBinding
import sky.tavrov.affapplication.ui.adapters.CartItemsListAdapter

class CartListActivity : BaseActivity() {

    private val binding by lazy { ActivityCartListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)

            setupActionBar(toolbarCartListActivity)
        }
    }

    override fun onResume() {
        super.onResume()

        getCartItemsList()
    }

    fun successCartItemsList(cartList: List<CartItem>) {
        hideProgressDialog()

        with(binding) {
            if (cartList.isNotEmpty()) {
                rvCartItemsList.visibility = View.VISIBLE
                llCheckout.visibility = View.VISIBLE
                tvNoCartItemFound.visibility = View.GONE

                rvCartItemsList.layoutManager = LinearLayoutManager(this@CartListActivity)
                rvCartItemsList.setHasFixedSize(true)
                val cartListAdapter = CartItemsListAdapter(this@CartListActivity, cartList)
                rvCartItemsList.adapter = cartListAdapter
                var subTotal = 0.0
                for (item in cartList) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()
                    subTotal += (price * quantity)
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

    private fun getCartItemsList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getCartList(this@CartListActivity)
    }

    private fun setupActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }


}