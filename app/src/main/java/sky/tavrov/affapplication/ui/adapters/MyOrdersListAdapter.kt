package sky.tavrov.affapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.data.models.Order
import sky.tavrov.affapplication.databinding.ItemListLayoutBinding
import sky.tavrov.affapplication.ui.utils.GlideLoader

class MyOrdersListAdapter(
        private val context: Context,
        private var list: ArrayList<Order>
) : RecyclerView.Adapter<MyOrdersListAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        with(holder.binding) {

            GlideLoader(context).loadProductPicture(
                    model.image,
                    ivItemImage
            )

            tvItemName.text = model.title
            tvItemPrice.text = "$${model.total_amount}"
            ibDeleteProduct.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}