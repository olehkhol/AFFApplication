package sky.tavrov.affapplication.ui.fragments

import androidx.fragment.app.Fragment
import sky.tavrov.affapplication.ui.custom.ProgressDialogWrapper

open class BaseFragment : Fragment() {

    private val progressDialogWrapper by lazy { ProgressDialogWrapper(requireContext()) }

    fun showProgressDialog(text: String) {
        progressDialogWrapper.show(text)
    }

    fun hideProgressDialog() {
        progressDialogWrapper.hide()
    }
}