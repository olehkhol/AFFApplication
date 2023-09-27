package sky.tavrov.affapplication.ui.fragments.products

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.FragmentProductsBinding
import sky.tavrov.affapplication.ui.activities.AddProductActivity
import sky.tavrov.affapplication.ui.adapters.MyProductsListAdapter
import sky.tavrov.affapplication.ui.fragments.BaseFragment

class ProductsFragment : BaseFragment() {

    //private val viewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }
    private val binding by lazy { FragmentProductsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_product -> {
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        getProductListFromFireStore()
    }

    private fun getProductListFromFireStore() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getProductsList(this@ProductsFragment)
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
        hideProgressDialog()

        if (productsList.size > 0) {
            binding.rvMyProductItems.visibility = View.VISIBLE
            binding.tvNoProductsFound.visibility = View.GONE

            binding.rvMyProductItems.layoutManager = LinearLayoutManager(activity)
            binding.rvMyProductItems.setHasFixedSize(true)

            val adapterProducts =
                MyProductsListAdapter(requireActivity(), productsList, this@ProductsFragment)
            binding.rvMyProductItems.adapter = adapterProducts
        } else {
            binding.rvMyProductItems.visibility = View.GONE
            binding.rvMyProductItems.visibility = View.VISIBLE
        }
    }

    fun deleteProduct(productId: String) {
        showAlertDialogToDeleteProduct(productId)
    }

    fun productDeleteSuccess() {
        hideProgressDialog()

        Toast.makeText(
            requireActivity(),
            resources.getString(R.string.product_delete_success_message),
            Toast.LENGTH_LONG
        ).show()

        getProductListFromFireStore()
    }

    private fun showAlertDialogToDeleteProduct(productId: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(resources.getString(R.string.delete_dialog_title))
            setMessage(resources.getString(R.string.delete_dialog_message))
            setIcon(android.R.drawable.ic_dialog_alert)
            setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _ ->
                showProgressDialog(resources.getString(R.string.please_wait))
                FirestoreClass().deleteProduct(this@ProductsFragment, productId)
                dialogInterface.dismiss()
            }
            setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            create().apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                show()
            }
        }
    }
}