package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import sky.tavrov.affapplication.data.models.Address
import sky.tavrov.affapplication.databinding.ActivityCheckoutBinding
import sky.tavrov.affapplication.ui.utils.Constants

class CheckoutActivity : BaseActivity() {

    private val binding by lazy { ActivityCheckoutBinding.inflate(layoutInflater) }
    private var addressDetails: Address? = null

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
}