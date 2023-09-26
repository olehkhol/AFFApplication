package sky.tavrov.affapplication.ui.fragments.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sky.tavrov.affapplication.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    //private val viewModel by lazy { ViewModelProvider(this)[NotificationsViewModel::class.java] }
    private val binding by lazy { FragmentOrdersBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.textNotifications.text = "This is orders Fragment"

        return binding.root
    }
}