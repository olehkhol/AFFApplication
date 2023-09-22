package sky.tavrov.affapplication.ui.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T: AppCompatActivity> Context.startActivityFor() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}