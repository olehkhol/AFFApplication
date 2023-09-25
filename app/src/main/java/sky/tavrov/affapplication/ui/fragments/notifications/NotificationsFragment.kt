package sky.tavrov.affapplication.ui.fragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import sky.tavrov.affapplication.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[NotificationsViewModel::class.java] }
    private val binding by lazy { FragmentNotificationsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }

        return binding.root
    }
}