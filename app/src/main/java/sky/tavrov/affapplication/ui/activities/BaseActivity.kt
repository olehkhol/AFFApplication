package sky.tavrov.affapplication.ui.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.ui.custom.ProgressDialogWrapper

open class BaseActivity : AppCompatActivity() {

    private val progressDialogWrapper by lazy { ProgressDialogWrapper(this) }

    fun showErrorSnackBar(message: String, isError: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (isError) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }

        snackBar.show()
    }

    fun showProgressDialog(text: String) {
        progressDialogWrapper.show(text)
    }

    fun hideProgressDialog() {
        progressDialogWrapper.hide()
    }
}
