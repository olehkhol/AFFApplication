package sky.tavrov.affapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.data.models.Product
import sky.tavrov.affapplication.databinding.ItemDashboardLayoutBinding
import sky.tavrov.affapplication.ui.utils.GlideLoader

open class DashboardItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>
) : RecyclerView.Adapter<DashboardItemsListAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemDashboardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onClick(position: Int, product: Product)
    }

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemDashboardLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        with(holder.binding) {
            GlideLoader(context).loadProductPicture(
                model.image,
                ivDashboardItemImage
            )
            tvDashboardItemTitle.text = model.title
            tvDashboardItemPrice.text = "$${model.price}"

            holder.itemView.setOnClickListener {
                onClickListener?.onClick(position, model)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }
}