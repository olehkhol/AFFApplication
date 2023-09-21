package sky.tavrov.affapplication.ui.custom

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import sky.tavrov.affapplication.databinding.DialogProgressBinding

class ProgressDialogWrapper(context: Context) {

    private val binding = DialogProgressBinding.inflate(LayoutInflater.from(context))
    private val dialog: Dialog = Dialog(context).apply {
        setContentView(binding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    fun show(text: String) {
        binding.tvProgressText.text = text
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }
}