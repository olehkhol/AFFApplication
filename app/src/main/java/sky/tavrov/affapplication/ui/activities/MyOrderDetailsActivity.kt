package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.models.Order
import sky.tavrov.affapplication.databinding.ActivityMyOrderDetailsBinding
import sky.tavrov.affapplication.ui.adapters.CartItemsListAdapter
import sky.tavrov.affapplication.ui.utils.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class MyOrderDetailsActivity : BaseActivity() {

    private val binding by lazy { ActivityMyOrderDetailsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)
            setupActionBar(toolbarMyOrderDetailsActivity)

            var orderDetails: Order = Order()
            if (intent.hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)) {
                orderDetails = intent.getParcelableExtra(Constants.EXTRA_MY_ORDER_DETAILS)!!
            }
            setupUI(orderDetails)
        }
    }

    private fun setupUI(order: Order) {
        binding.tvOrderDetailsId.text = order.title

        val dateFormat = "dd MMM yyyy HH:mm"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = order.order_datetime
        val orderDateTime = formatter.format(calendar.time)
        binding.tvOrderDetailsDate.text = orderDateTime

        val diffInMilliSeconds: Long = System.currentTimeMillis() - order.order_datetime
        val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMilliSeconds)
        when {
            diffInHours < 1 -> {
                binding.tvOrderStatus.text = resources.getString(R.string.order_status_pending)
                binding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorAccent
                    )
                )
            }

            diffInHours < 2 -> {
                binding.tvOrderStatus.text = resources.getString(R.string.order_status_in_process)
                binding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorOrderStatusInProcess
                    )
                )
            }

            else -> {
                binding.tvOrderStatus.text = resources.getString(R.string.order_status_delivered)
                binding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorOrderStatusDelivered
                    )
                )
            }
        }

        binding.rvMyOrderItemsList.layoutManager = LinearLayoutManager(this@MyOrderDetailsActivity)
        binding.rvMyOrderItemsList.setHasFixedSize(true)

        val cartListAdapter =
            CartItemsListAdapter(this@MyOrderDetailsActivity, order.items, false)
        binding.rvMyOrderItemsList.adapter = cartListAdapter

        binding.tvMyOrderDetailsAddressType.text = order.address.type
        binding.tvMyOrderDetailsFullName.text = order.address.name
        binding.tvMyOrderDetailsAddress.text =
            "${order.address.address}, ${order.address.zipCode}"
        binding.tvMyOrderDetailsAdditionalNote.text = order.address.additionalNote

        if (order.address.otherDetails.isNotEmpty()) {
            binding.tvMyOrderDetailsOtherDetails.visibility = View.VISIBLE
            binding.tvMyOrderDetailsOtherDetails.text = order.address.otherDetails
        } else {
            binding.tvMyOrderDetailsOtherDetails.visibility = View.GONE
        }
        binding.tvMyOrderDetailsMobileNumber.text = order.address.mobileNumber

        binding.tvOrderDetailsSubTotal.text = order.sub_total_amount
        binding.tvOrderDetailsShippingCharge.text = order.shipping_charge
        binding.tvOrderDetailsTotalAmount.text = order.total_amount
    }
}