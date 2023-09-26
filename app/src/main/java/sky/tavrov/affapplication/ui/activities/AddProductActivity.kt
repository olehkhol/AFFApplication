package sky.tavrov.affapplication.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.ActivityAddProductBinding
import sky.tavrov.affapplication.ui.utils.Constants
import sky.tavrov.affapplication.ui.utils.GlideLoader
import sky.tavrov.affapplication.ui.utils.trimmedText
import java.io.IOException

class AddProductActivity : BaseActivity() {

    private val binding by lazy { ActivityAddProductBinding.inflate(layoutInflater) }
    private var selectedImageFileUri: Uri? = null
    private var productImageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setContentView(binding.root)

            setupActionBar()

            ivAddUpdateProduct.setOnClickListener {
                val permissionToCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }

                if (ContextCompat.checkSelfPermission(
                        this@AddProductActivity,
                        permissionToCheck
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Constants.showImageChooser(this@AddProductActivity)
                } else {
                    ActivityCompat.requestPermissions(
                        this@AddProductActivity,
                        arrayOf(permissionToCheck),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
            btnSubmit.setOnClickListener {
                if (validateProductsDetails()) {
                    uploadProductImage()
                }
            }
        }
    }

    private fun uploadProductImage() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().uploadImageToCloudStorage(
            this@AddProductActivity,
            selectedImageFileUri,
            Constants.PRODUCT_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {

            binding.ivAddUpdateProduct.setImageDrawable(
                ContextCompat.getDrawable(
                    this@AddProductActivity,
                    R.drawable.ic_vector_edit
                )
            )

            selectedImageFileUri = data.data!!

            try {
                GlideLoader(this@AddProductActivity).loadProductPicture(
                    selectedImageFileUri!!,
                    binding.ivProductImage
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun imageUploadSuccess(imageUrl: String) {

        // Initialize the global image url variable.
        productImageUrl = imageUrl

        uploadProductDetails()
    }

    private fun uploadProductDetails() {
        val username =
            this.getSharedPreferences(Constants.MY_SHOP_PAL_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.LOGGED_IN_USERNAME, "")!!

        val product = Product(
            FirestoreClass().getCurrentUserID(),
            username,
            binding.etProductTitle.trimmedText(),
            binding.etProductPrice.trimmedText(),
            binding.etProductDescription.trimmedText(),
            binding.etProductQuantity.trimmedText(),
            productImageUrl
        )

        FirestoreClass().uploadProductDetails(this@AddProductActivity, product)
    }

    private fun validateProductsDetails(): Boolean {
        return when {
            selectedImageFileUri == null -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
                false
            }

            TextUtils.isEmpty(binding.etProductTitle.trimmedText()) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_title), true)
                false
            }

            TextUtils.isEmpty(binding.etProductPrice.trimmedText()) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_price), true)
                false
            }

            TextUtils.isEmpty(binding.etProductDescription.trimmedText()) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_product_description),
                    true
                )
                false
            }

            TextUtils.isEmpty(binding.etProductQuantity.trimmedText()) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_product_quantity),
                    true
                )
                false
            }

            else -> {
                true
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this@AddProductActivity)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarAddProductActivity)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        binding.toolbarAddProductActivity.setNavigationOnClickListener { onBackPressed() }
    }

    fun productUploadSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@AddProductActivity,
            resources.getString(R.string.product_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }
}