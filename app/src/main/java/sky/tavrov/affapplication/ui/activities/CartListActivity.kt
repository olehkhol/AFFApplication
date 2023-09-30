package sky.tavrov.affapplication.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.databinding.ActivityCartListBinding

class CartListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCartListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}