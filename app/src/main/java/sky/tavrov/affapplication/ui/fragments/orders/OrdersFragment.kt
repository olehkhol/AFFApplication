package sky.tavrov.affapplication.ui.fragments.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Order
import sky.tavrov.affapplication.databinding.FragmentOrdersBinding
import sky.tavrov.affapplication.ui.adapters.MyOrdersListAdapter
import sky.tavrov.affapplication.ui.fragments.BaseFragment

class OrdersFragment : BaseFragment() {

    private val binding by lazy { FragmentOrdersBinding.inflate(layoutInflater) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        getMyOrdersList()
    }

    private fun getMyOrdersList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getMyOrdersList(this@OrdersFragment)
    }

    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {
        hideProgressDialog()

        with(binding) {
            if (ordersList.size > 0) {

                rvMyOrderItems.visibility = View.VISIBLE
                tvNoOrdersFound.visibility = View.GONE

                rvMyOrderItems.layoutManager = LinearLayoutManager(activity)
                rvMyOrderItems.setHasFixedSize(true)

                val myOrdersAdapter = MyOrdersListAdapter(requireActivity(), ordersList)
                rvMyOrderItems.adapter = myOrdersAdapter
            } else {
                rvMyOrderItems.visibility = View.GONE
                tvNoOrdersFound.visibility = View.VISIBLE
            }
        }
    }
}