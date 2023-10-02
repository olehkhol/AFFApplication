package sky.tavrov.affapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.R
import sky.tavrov.affapplication.data.firestore.FirestoreClass
import sky.tavrov.affapplication.data.models.CartItem
import sky.tavrov.affapplication.databinding.ItemCartLayoutBinding
import sky.tavrov.affapplication.ui.activities.CartListActivity
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

            if (model.cart_quantity == "0") {
                ibRemoveCartItem.visibility = View.GONE
                ibAddCartItem.visibility = View.GONE
                tvCartQuantity.text = context.resources.getString(R.string.lbl_out_of_stock)
                tvCartQuantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSnackBarError
                    )
                )
            } else {
                ibRemoveCartItem.visibility = View.VISIBLE
                ibAddCartItem.visibility = View.VISIBLE

                tvCartQuantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSecondaryText
                    )
                )
            }

            ibDeleteCartItem.setOnClickListener {
                when (context) {
                    is CartListActivity -> {
                        context.showProgressDialog(
                            context.resources.getString(R.string.please_wait)
                        )
                    }
                }

                FirestoreClass().removeItemFromCart(context, model.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}