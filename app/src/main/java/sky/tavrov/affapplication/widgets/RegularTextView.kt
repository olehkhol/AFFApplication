package sky.tavrov.affapplication.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class RegularTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    init {
        applyFont()
    }

    private fun applyFont() {
        typeface = Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
    }
}