package sky.tavrov.affapplication.ui.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {

    // User related constants
    const val USERS: String = "users"
    const val USER_ID: String = "user_id"
    const val EMAIL: String = "email"
    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"
    const val MALE: String = "male"
    const val FEMALE: String = "female"
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val IMAGE: String = "image"
    const val COMPLETE_PROFILE = "profileCompleted"
    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val HOME: String = "Home"
    const val OFFICE: String = "Office"
    const val OTHER: String = "Other"
    const val ADDRESSES: String = "addresses"
    const val EXTRA_ADDRESS_DETAILS: String = "AddressDetails"
    const val EXTRA_SELECT_ADDRESS: String = "extra_select_address"
    const val EXTRA_SELECTED_ADDRESS: String = "extra_selected_address"
    const val ADD_ADDRESS_REQUEST_CODE: Int = 121

    // Product related constants
    const val PRODUCTS: String = "products"
    const val PRODUCT_ID: String = "product_id"
    const val PRODUCT_IMAGE: String = "Product_Image"
    const val EXTRA_PRODUCT_ID: String = "extra_product_id"
    const val EXTRA_PRODUCT_OWNER_ID: String = "extra_product_owner_id"

    // Cart related constants
    const val DEFAULT_CART_QUANTITY: String = "1"
    const val CART_ITEMS: String = "cart_items"
    const val CART_QUANTITY: String = "cart_quantity"

    // App related constants
    const val MY_SHOP_PAL_PREFERENCES: String = "MyShopPalPrefs"
    const val READ_STORAGE_PERMISSION_CODE: Int = 2
    const val PICK_IMAGE_REQUEST_CODE: Int = 1

    // Utility functions
    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(uri?.let { activity.contentResolver.getType(it) })
    }
}