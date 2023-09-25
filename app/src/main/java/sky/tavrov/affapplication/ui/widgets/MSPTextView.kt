package sky.tavrov.affapplication.ui.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MSPTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    init {
        applyFont()
    }

    private fun applyFont() {
        typeface = Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
    }
}