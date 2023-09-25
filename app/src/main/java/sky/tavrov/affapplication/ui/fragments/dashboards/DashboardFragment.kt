package sky.tavrov.affapplication.ui.fragments.dashboards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import sky.tavrov.affapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[DashboardViewModel::class.java] }
    private val binding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textDashboard.text = it
        }

        return binding.root
    }
}