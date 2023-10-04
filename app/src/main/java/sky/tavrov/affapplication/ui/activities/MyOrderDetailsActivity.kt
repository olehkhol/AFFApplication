package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import sky.tavrov.affapplication.data.models.Order
import sky.tavrov.affapplication.databinding.ActivityMyOrderDetailsBinding
import sky.tavrov.affapplication.ui.utils.Constants

class MyOrderDetailsActivity : BaseActivity() {

    private val binding by lazy { ActivityMyOrderDetailsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)
            setupActionBar(toolbarMyOrderDetailsActivity)

            val orderDetails: Order
            if (intent.hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)) {
                orderDetails = intent.getParcelableExtra(Constants.EXTRA_MY_ORDER_DETAILS)!!
            }
        }
    }
}