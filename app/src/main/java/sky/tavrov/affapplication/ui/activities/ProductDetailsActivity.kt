package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import sky.tavrov.affapplication.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : BaseActivity() {

    private val binding by lazy { ActivityProductDetailsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}