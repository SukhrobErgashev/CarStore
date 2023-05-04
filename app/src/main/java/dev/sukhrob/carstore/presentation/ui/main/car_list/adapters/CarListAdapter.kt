package dev.sukhrob.carstore.presentation.ui.main.car_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ItemCarListBinding
import dev.sukhrob.carstore.domain.model.Car

class CarListAdapter(private val list: List<Car>) :
    RecyclerView.Adapter<CarListAdapter.CarListVH>() {

    var listener: ((carId: String, price: Long) -> Unit)? = null

    inner class CarListVH(private val binding: ItemCarListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener?.let { it1 ->
                    it1(
                        list[bindingAdapterPosition].carId!!,
                        list[bindingAdapterPosition].price!!
                    )
                }
            }
        }

        fun bind() {
            with(binding) {
                imageCar.load(list[bindingAdapterPosition].carImage) {
                    crossfade(true)
                }
                textModel.text = list[bindingAdapterPosition].model
                textOriginCountry.text = list[bindingAdapterPosition].originCountry
                textYear.text = list[bindingAdapterPosition].originYear
                textPrice.text = "${list[bindingAdapterPosition].price} $"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListVH {
        return CarListVH(
            ItemCarListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CarListVH, position: Int) {
        holder.bind()
    }

}