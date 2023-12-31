package sky.tavrov.affapplication.ui.activities

import android.app.Activity
import android.content.Intent
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
            if (addressDetails != null) {
                if (addressDetails!!.id.isNotEmpty()) {
                    tvTitle.text = resources.getString(R.string.title_edit_address)
                    btnSubmitAddress.text = resources.getString(R.string.btn_lbl_update)

                    etFullName.setText(addressDetails?.name)
                    etPhoneNumber.setText(addressDetails?.mobileNumber)
                    etAddress.setText(addressDetails?.address)
                    etZipCode.setText(addressDetails?.zipCode)
                    etAdditionalNote.setText(addressDetails?.additionalNote)

                    when (addressDetails?.type) {
                        Constants.HOME -> {
                            rbHome.isChecked = true
                        }

                        Constants.OFFICE -> {
                            rbOffice.isChecked = true
                        }

                        else -> {
                            rbOther.isChecked = true
                            tilOtherDetails.visibility = View.VISIBLE
                            etOtherDetails.setText(addressDetails?.otherDetails)
                        }
                    }
                }
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

                if (addressDetails != null && addressDetails!!.id.isNotEmpty()) {
                    FirestoreClass().updateAddress(
                        this@AddEditAddressActivity,
                        addressModel,
                        addressDetails!!.id
                    )
                } else {
                    FirestoreClass().addAddress(
                        this@AddEditAddressActivity,
                        addressModel
                    )
                }

                FirestoreClass().addAddress(this@AddEditAddressActivity, addressModel)
            }
        }
    }

    fun addUpdateAddressSuccess() {
        hideProgressDialog()

        val notifySuccessMessage: String = if (addressDetails != null && addressDetails!!.id.isNotEmpty()) {
            resources.getString(R.string.msg_your_address_updated_successfully)
        } else {
            resources.getString(R.string.err_your_address_added_successfully)
        }

        Toast.makeText(
            this@AddEditAddressActivity,
            notifySuccessMessage,
            Toast.LENGTH_SHORT
        ).show()
        setResult(RESULT_OK)
        finish()
    }
}