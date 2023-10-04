package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import android.view.View
import sky.tavrov.affapplication.data.models.SoldProduct
import sky.tavrov.affapplication.databinding.ActivitySoldProductDetailsBinding
import sky.tavrov.affapplication.ui.utils.Constants
import sky.tavrov.affapplication.ui.utils.GlideLoader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SoldProductDetailsActivity : BaseActivity() {

    private val binding by lazy { ActivitySoldProductDetailsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)
            setupActionBar(toolbarSoldProductDetailsActivity)

            var productDetails = SoldProduct()
            if (intent.hasExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS)) {
                productDetails = intent.getParcelableExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS)!!
            }
            setupUI(productDetails)
        }
    }

    private fun setupUI(productDetails: SoldProduct) {
        with(binding) {
            tvSoldProductDetailsId.text = productDetails.order_id
            val dateFormat = "dd MMM yyyy HH:mm"
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = productDetails.order_date
            tvSoldProductDetailsDate.text = formatter.format(calendar.time)

            GlideLoader(this@SoldProductDetailsActivity).loadProductPicture(
                productDetails.image,
                ivProductItemImage
            )
            tvProductItemName.text = productDetails.title
            tvProductItemPrice.text = "$${productDetails.price}"
            tvSoldProductQuantity.text = productDetails.sold_quantity

            tvSoldDetailsAddressType.text = productDetails.address.type
            tvSoldDetailsFullName.text = productDetails.address.name
            tvSoldDetailsAddress.text =
                "${productDetails.address.address}, ${productDetails.address.zipCode}"
            tvSoldDetailsAdditionalNote.text = productDetails.address.additionalNote

            if (productDetails.address.otherDetails.isNotEmpty()) {
                tvSoldDetailsOtherDetails.visibility = View.VISIBLE
                tvSoldDetailsOtherDetails.text = productDetails.address.otherDetails
            } else {
                tvSoldDetailsOtherDetails.visibility = View.GONE
            }
            tvSoldDetailsMobileNumber.text = productDetails.address.mobileNumber

            tvSoldProductSubTotal.text = productDetails.sub_total_amount
            tvSoldProductShippingCharge.text = productDetails.shipping_charge
            tvSoldProductTotalAmount.text = productDetails.total_amount
        }
    }
}