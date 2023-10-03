package sky.tavrov.affapplication.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val userId: String = "",
    val name: String = "",
    val mobileNumber: String = "",
    val address: String = "",
    val zipCode: String = "",
    val additionalNote: String = "",
    val type: String = "",
    val otherDetails: String = "",
    var id: String = ""
) : Parcelable