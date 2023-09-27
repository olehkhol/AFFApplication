package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import android.util.Log
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.databinding.ActivityProductDetailsBinding
import sky.tavrov.affapplication.ui.utils.Constants

class ProductDetailsActivity : BaseActivity() {

    private val binding by lazy { ActivityProductDetailsBinding.inflate(layoutInflater) }
    private var productId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)

            setupActionBar()

            if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
                productId = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
                Log.i("Product Id:", productId)
            }
        }
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
}