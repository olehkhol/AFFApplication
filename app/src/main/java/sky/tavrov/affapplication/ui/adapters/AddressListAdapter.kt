package sky.tavrov.affapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sky.tavrov.affapplication.data.models.Address
import sky.tavrov.affapplication.databinding.ItemAddressLayoutBinding

class AddressListAdapter(
    private val context: Context,
    private var list: List<Address>
) : RecyclerView.Adapter<AddressListAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemAddressLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemAddressLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return AddressListAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        with(holder.binding) {
            tvAddressFullName.text = model.name
            tvAddressType.text = model.type
            tvAddressDetails.text = "${model.address}, ${model.zipCode}"
            tvAddressMobileNumber.text = model.mobileNumber
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}