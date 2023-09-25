package sky.tavrov.affapplication.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sky.tavrov.affapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    //private val viewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.textHome.text = "This is home Fragment"

        return binding.root
    }
}