package sky.tavrov.affapplication.ui.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.data.models.Address
import sky.tavrov.affapplication.databinding.ItemAddressLayoutBinding
import sky.tavrov.affapplication.ui.activities.AddEditAddressActivity
import sky.tavrov.affapplication.ui.activities.CheckoutActivity
import sky.tavrov.affapplication.ui.utils.Constants

class AddressListAdapter(
    private val context: Context,
    private var list: List<Address>,
    private val selectAddress: Boolean
) : RecyclerView.Adapter<AddressListAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemAddressLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemAddressLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        with(holder) {
            binding.tvAddressFullName.text = model.name
            binding.tvAddressType.text = model.type
            binding.tvAddressDetails.text = "${model.address}, ${model.zipCode}"
            binding.tvAddressMobileNumber.text = model.mobileNumber

            if (selectAddress) {
                itemView.setOnClickListener {
                    val intent = Intent(context, CheckoutActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun notifyEditItem(activity: Activity, position: Int) {
        val intent = Intent(context, AddEditAddressActivity::class.java)
        intent.putExtra(Constants.EXTRA_ADDRESS_DETAILS, list[position])
        activity.startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)

        notifyItemChanged(position)
    }
}