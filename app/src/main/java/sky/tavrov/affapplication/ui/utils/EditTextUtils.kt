package sky.tavrov.affapplication.ui.utils

import android.widget.EditText

fun EditText.trimmedText(): String = this.text.toString().trim { it <= ' ' }