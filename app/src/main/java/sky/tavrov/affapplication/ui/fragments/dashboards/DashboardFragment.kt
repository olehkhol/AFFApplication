package sky.tavrov.affapplication.ui.fragments.dashboards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.FragmentDashboardBinding
import sky.tavrov.affapplication.ui.activities.SettingsActivity
import sky.tavrov.affapplication.ui.adapters.DashboardItemsListAdapter
import sky.tavrov.affapplication.ui.fragments.BaseFragment

class DashboardFragment : BaseFragment() {

    //private val viewModel by lazy { ViewModelProvider(this)[DashboardViewModel::class.java] }
    private val binding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()

        getDashboardItemsList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {
        hideProgressDialog()

        with(binding) {
            if (dashboardItemsList.isNotEmpty()) {
                rvDashboardItems.apply {
                    visibility = View.VISIBLE
                    layoutManager = GridLayoutManager(activity, 2)
                    setHasFixedSize(true)
                    adapter = DashboardItemsListAdapter(requireContext(), dashboardItemsList)
                }
                tvNoDashboardItemsFound.visibility = View.GONE
            } else {
                rvDashboardItems.visibility = View.GONE
                tvNoDashboardItemsFound.visibility = View.VISIBLE
            }
        }
    }

    private fun getDashboardItemsList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getDashboardItemsList(this@DashboardFragment)
    }
}