package sky.tavrov.affapplication.ui.utils

fun String.trimWhitespace(): String = this.trim { it <= ' ' }