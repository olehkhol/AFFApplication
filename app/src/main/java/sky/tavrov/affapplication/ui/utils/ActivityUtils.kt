package sky.tavrov.affapplication.ui.utils

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : AppCompatActivity> Context.startActivityFor(extra: Parcelable? = null) {
    val intent = Intent(this, T::class.java)
    extra?.let {
        intent.putExtra(Constants.EXTRA_USER_DETAILS, it)
    }
    startActivity(intent)
}