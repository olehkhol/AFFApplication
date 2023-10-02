package sky.tavrov.affapplication.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.CartItem
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.ActivityProductDetailsBinding
import sky.tavrov.affapplication.ui.utils.Constants
import sky.tavrov.affapplication.ui.utils.GlideLoader

class ProductDetailsActivity : BaseActivity() {

    private val binding by lazy { ActivityProductDetailsBinding.inflate(layoutInflater) }
    private var productId: String = ""
    private var productOwnerId: String = ""
    private lateinit var productDetails: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)

            setupActionBar()

            if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
                productId = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
                Log.i("Product Id:", productId)
            }
            if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
                productOwnerId = intent.getStringExtra(Constants.EXTRA_USER_DETAILS)!!
                Log.i("Owner Id:", productOwnerId)
            }

            btnAddToCart.visibility =
                if (FirestoreClass().getCurrentUserID() == productOwnerId) View.GONE else View.VISIBLE
            btnAddToCart.setOnClickListener { addToCart() }
            btnGoToCart.setOnClickListener { goToCart() }
        }
    }

    override fun onResume() {
        super.onResume()

        getProductDetails()
    }

    private fun goToCart() {
        startActivity(Intent(this@ProductDetailsActivity, CartListActivity::class.java))
    }

    private fun addToCart() {
        val addToCart = CartItem(
            FirestoreClass().getCurrentUserID(),
            productId,
            productDetails.title,
            productDetails.price,
            productDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().addCartItems(this@ProductDetailsActivity, addToCart)
    }

    fun addToCartSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@ProductDetailsActivity,
            resources.getString(R.string.success_message_item_added_to_cart),
            Toast.LENGTH_LONG
        ).show()


        binding.btnAddToCart.visibility = View.GONE
        binding.btnGoToCart.visibility = View.VISIBLE
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarProductDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarProductDetailsActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getProductDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getProductDetails(this@ProductDetailsActivity, productId)
    }

    fun productDetailsSuccess(product: Product) {
        productDetails = product

        GlideLoader(this@ProductDetailsActivity).loadProductPicture(
            product.image,
            binding.ivProductDetailImage
        )
        binding.tvProductDetailsTitle.text = product.title
        binding.tvProductDetailsPrice.text = "$${product.price}"
        binding.tvProductDetailsDescription.text = product.description
        binding.tvProductDetailsStockQuantity.text = product.stock_quantity

        if (FirestoreClass().getCurrentUserID() == product.user_id) {
            hideProgressDialog()
        } else {
            FirestoreClass().checkIfItemExistInCart(this, productId)
        }
    }

    fun productExistsInCart() {
        hideProgressDialog()

        binding.btnAddToCart.visibility = View.GONE
        binding.btnGoToCart.visibility = View.VISIBLE
    }
}