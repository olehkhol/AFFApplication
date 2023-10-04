package sky.tavrov.affapplication.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.data.models.SoldProduct
import sky.tavrov.affapplication.databinding.ItemListLayoutBinding
import sky.tavrov.affapplication.ui.activities.SoldProductDetailsActivity
import sky.tavrov.affapplication.ui.utils.Constants
import sky.tavrov.affapplication.ui.utils.GlideLoader

class SoldProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<SoldProduct>
) : RecyclerView.Adapter<SoldProductsListAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        with(holder) {

            GlideLoader(context).loadProductPicture(
                model.image,
                binding.ivItemImage
            )

            binding.tvItemName.text = model.title
            binding.tvItemPrice.text = "$${model.price}"
            binding.ibDeleteProduct.visibility = View.GONE

            holder.itemView.setOnClickListener {
                val intent = Intent(context, SoldProductDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS, model)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}