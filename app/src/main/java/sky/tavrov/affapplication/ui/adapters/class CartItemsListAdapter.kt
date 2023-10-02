package sky.tavrov.affapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.data.models.CartItem
import sky.tavrov.affapplication.databinding.ItemCartLayoutBinding
import sky.tavrov.affapplication.ui.utils.GlideLoader

class CartItemsListAdapter(
    private val context: Context,
    private var list: List<CartItem>
) : RecyclerView.Adapter<CartItemsListAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemCartLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemCartLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        with(holder.binding) {
            GlideLoader(context).loadProductPicture(model.image, ivCartItemImage)
            tvCartItemTitle.text = model.title
            tvCartItemPrice.text = "$${model.price}"
            tvCartQuantity.text = model.cart_quantity
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}