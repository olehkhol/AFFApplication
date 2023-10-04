package sky.tavrov.affapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.SoldProduct
import sky.tavrov.affapplication.databinding.FragmentSoldProductsBinding
import sky.tavrov.affapplication.ui.adapters.SoldProductsListAdapter

class SoldProductsFragment : BaseFragment() {

    private val binding by lazy { FragmentSoldProductsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        getSoldProductsList()
    }

    private fun getSoldProductsList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getSoldProductsList(this@SoldProductsFragment)
    }

    fun successSoldProductsList(list: ArrayList<SoldProduct>) {
        hideProgressDialog()

        if (list.size > 0) {
            binding.rvSoldProductItems.visibility = View.VISIBLE
            binding.tvNoSoldProductsFound.visibility = View.GONE

            binding.rvSoldProductItems.layoutManager = LinearLayoutManager(activity)
            binding.rvSoldProductItems.setHasFixedSize(true)

            val soldProductsListAdapter =
                SoldProductsListAdapter(requireActivity(), list)
            binding.rvSoldProductItems.adapter = soldProductsListAdapter
        } else {
            binding.rvSoldProductItems.visibility = View.GONE
            binding.tvNoSoldProductsFound.visibility = View.VISIBLE
        }
    }
}