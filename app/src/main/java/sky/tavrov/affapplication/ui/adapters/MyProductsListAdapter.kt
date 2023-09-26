package sky.tavrov.affapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.ItemListLayoutBinding
import sky.tavrov.affapplication.ui.fragments.products.ProductsFragment
import sky.tavrov.affapplication.ui.utils.GlideLoader

open class MyProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment: ProductsFragment
) : RecyclerView.Adapter<MyProductsListAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = list[position]

        with(holder.binding) {
            GlideLoader(context).loadProductPicture(product.image, ivItemImage)
            tvItemName.text = product.title
            tvItemPrice.text = "$${product.price}"
            ibDeleteProduct.setOnClickListener {
                fragment.deleteProduct(product.product_id)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}