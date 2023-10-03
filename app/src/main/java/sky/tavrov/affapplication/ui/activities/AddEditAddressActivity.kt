package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Address
import sky.tavrov.affapplication.databinding.ActivityAddEditAddressBinding
import sky.tavrov.affapplication.ui.utils.Constants
import sky.tavrov.affapplication.ui.utils.trimmedText

class AddEditAddressActivity : BaseActivity() {

    private val binding by lazy { ActivityAddEditAddressBinding.inflate(layoutInflater) }
    private var addressDetails: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(root)
            setupActionBar(toolbarAddEditAddressActivity)

            if (intent.hasExtra(Constants.EXTRA_ADDRESS_DETAILS)) {
                addressDetails = intent.getParcelableExtra(Constants.EXTRA_ADDRESS_DETAILS)
            }

            btnSubmitAddress.setOnClickListener { saveAddressToFirestore() }
            rgType.setOnCheckedChangeListener { _, checkedId ->
                tilOtherDetails.visibility =
                    if (checkedId == R.id.rb_other) View.VISIBLE else View.GONE
            }
        }
    }

    private fun validateData(): Boolean {
        with(binding) {
            return when {
                TextUtils.isEmpty(etFullName.trimmedText()) -> {
                    showErrorSnackBar(
                        resources.getString(R.string.err_msg_please_enter_full_name),
                        true
                    )
                    false
                }

                TextUtils.isEmpty(etPhoneNumber.trimmedText()) -> {
                    showErrorSnackBar(
                        resources.getString(R.string.err_msg_please_enter_phone_number),
                        true
                    )
                    false
                }

                TextUtils.isEmpty(etAddress.trimmedText()) -> {
                    showErrorSnackBar(
                        resources.getString(R.string.err_msg_please_enter_address),
                        true
                    )
                    false
                }

                TextUtils.isEmpty(etZipCode.trimmedText()) -> {
                    showErrorSnackBar(
                        resources.getString(R.string.err_msg_please_enter_zip_code),
                        true
                    )
                    false
                }

                rbOther.isChecked && TextUtils.isEmpty(
                    etZipCode.trimmedText()
                ) -> {
                    showErrorSnackBar(
                        resources.getString(R.string.err_msg_please_enter_zip_code),
                        true
                    )
                    false
                }

                else -> {
                    true
                }
            }
        }
    }

    private fun saveAddressToFirestore() {
        with(binding) {
            val fullName: String = etFullName.trimmedText()
            val phoneNumber: String = etPhoneNumber.trimmedText()
            val address: String = etAddress.trimmedText()
            val zipCode: String = etZipCode.trimmedText()
            val additionalNote: String = etAdditionalNote.trimmedText()
            val otherDetails: String = etOtherDetails.trimmedText()

            if (validateData()) {
                showProgressDialog(resources.getString(R.string.please_wait))

                val addressType: String = when {
                    rbHome.isChecked -> {
                        Constants.HOME
                    }

                    rbOffice.isChecked -> {
                        Constants.OFFICE
                    }

                    else -> {
                        Constants.OTHER
                    }
                }
                val addressModel = Address(
                    FirestoreClass().getCurrentUserID(),
                    fullName,
                    phoneNumber,
                    address,
                    zipCode,
                    additionalNote,
                    addressType,
                    otherDetails
                )

                FirestoreClass().addAddress(this@AddEditAddressActivity, addressModel)
            }
        }
    }

    fun addUpdateAddressSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@AddEditAddressActivity,
            resources.getString(R.string.err_your_address_added_successfully),
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }
}