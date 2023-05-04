package dev.sukhrob.carstore.presentation.ui.main.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sukhrob.carstore.databinding.ItemOrderListBinding
import dev.sukhrob.carstore.domain.model.Order

class OrdersAdapter(private val list: List<Order>) :
    RecyclerView.Adapter<OrdersAdapter.OrdersVH>() {

    inner class OrdersVH(private val binding: ItemOrderListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val order = list[bindingAdapterPosition]
            with(binding) {
                textCustomerName.text = order.customerFullName
                textCustomerNumber.text = order.customerPhoneNumber
                textCarModel.text = order.carId?.uppercase()
                textCarColor.text = order.color
                textEngineType.text = order.engineType
                textRemoteController.text =
                    if (order.remoteController == true) "INSTALLED" else "NOT_INSTALLED"
                textSoundroof.text = if (order.soundproof == true) "INSTALLED" else "NOT_INSTALLED"
                textPrimaryPrice.text = order.primaryPrice.toString()
                textTotalPrice.text = order.totalPrice.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersVH {
        return OrdersVH(
            ItemOrderListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: OrdersVH, position: Int) {
        holder.bind()
    }

}