package dev.sukhrob.carstore.presentation.ui.main.car_list.place_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firestore.v1.StructuredQuery.Order
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ScreenPlaceOrderBinding
import dev.sukhrob.carstore.enums.EngineType
import dev.sukhrob.carstore.enums.RemoteControl
import dev.sukhrob.carstore.presentation.ui.main.MainVM
import dev.sukhrob.carstore.utils.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaceOrderScreen: Fragment(R.layout.screen_place_order) {

    private val viewModel: MainVM by viewModels()
    private val navArgs: PlaceOrderScreenArgs by navArgs()
    private val binding by viewBinding(ScreenPlaceOrderBinding::bind)

    private var currentPrice: Long = 0

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textCarModel.text = "Car Model: ${(navArgs.carId)}"
        currentPrice = navArgs.price

        val adapterColors =
            ArrayAdapter(
                requireContext(),
                R.layout.item_autocomplete_layout,
                Constants.optionColors
            )

        val adapterEngineType =
            ArrayAdapter(
                requireContext(),
                R.layout.item_autocomplete_layout,
                Constants.optionEngineTypes
            )

        val adapterUniversal =
            ArrayAdapter(
                requireContext(),
                R.layout.item_autocomplete_layout,
                Constants.optionUniversal
            )

        val adapterWheelDisk =
            ArrayAdapter(
                requireContext(),
                R.layout.item_autocomplete_layout,
                Constants.optionWheelDisk
            )

        with(binding) {
            etColor.setAdapter(adapterColors)
            etEngineType.setAdapter(adapterEngineType)
            etRemoteControl.setAdapter(adapterUniversal)
            etSoundproof.setAdapter(adapterUniversal)
            etSunroof.setAdapter(adapterUniversal)
            etTinting.setAdapter(adapterUniversal)
            etVideoRecorder.setAdapter(adapterUniversal)
            etWheelDisk.setAdapter(adapterWheelDisk)
        }

        binding.btnContinue.setOnClickListener {
            val order = dev.sukhrob.carstore.domain.model.Order()

            order.carId = navArgs.carId

            if (binding.etColor.text.toString().isNotEmpty()) {
                order.color = binding.etColor.text.toString()
            } else {
                order.color = "WHITE"
            }

            if (binding.etEngineType.text.toString().isNotEmpty()) {
                order.engineType = binding.etEngineType.text.toString()
                if (order.engineType == EngineType.AUTOMATIC.name) {
                    currentPrice = (currentPrice + currentPrice*0.01).toLong()
                }
            } else {
                order.engineType = "MANUAL"
            }

            if (binding.etRemoteControl.text.toString().isNotEmpty()) {
                order.remoteController = binding.etRemoteControl.text.toString() == "INSTALLED"
                if (order.remoteController == true) {
                    currentPrice = (currentPrice + currentPrice*0.01).toLong()
                }
            } else {
                order.remoteController = false
            }

            if (binding.etSoundproof.text.toString().isNotEmpty()) {
                order.soundproof = binding.etSoundproof.text.toString() == "INSTALLED"
            } else {
                order.soundproof = false
            }

            if (binding.etTinting.text.toString().isNotEmpty()) {
                order.tinting = binding.etTinting.text.toString() == "INSTALLED"
            } else {
                order.tinting = false
            }

            if (binding.etVideoRecorder.text.toString().isNotEmpty()) {
                order.videoRecorder = binding.etVideoRecorder.text.toString() == "INSTALLED"
            } else {
                order.videoRecorder = false
            }

            if (binding.etWheelDisk.text.toString().isNotEmpty()) {
                order.wheelDisk = binding.etWheelDisk.text.toString() == "CHANGED"
            } else {
                order.wheelDisk = false
            }

            findNavController().navigate(PlaceOrderScreenDirections.actionPlaceOrderScreenToCalculatePriceScreen(order, navArgs.price))

        }
    }

}