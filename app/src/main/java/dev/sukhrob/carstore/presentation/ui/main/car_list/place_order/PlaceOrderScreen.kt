package dev.sukhrob.carstore.presentation.ui.main.car_list.place_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import dev.sukhrob.carstore.enums.*
import dev.sukhrob.carstore.presentation.ui.main.MainVM
import dev.sukhrob.carstore.utils.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaceOrderScreen : Fragment(R.layout.screen_place_order) {

    private val viewModel: MainVM by viewModels()
    private val navArgs: PlaceOrderScreenArgs by navArgs()
    private val binding by viewBinding(ScreenPlaceOrderBinding::bind)

    private var totalPrice: Long = 0
    private var aa = 8

    private var colorState = false
    private var engineTypeState = false
    private var remoteControllerState = false
    private var soundproofState = false
    private var tintingState = false
    private var videoRecorderState = false
    private var wheelDiskState = false

    val order = dev.sukhrob.carstore.domain.model.Order()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textCarModel.text = getString(R.string.car_model, navArgs.carId.uppercase())
        totalPrice = navArgs.price
        order.primaryPrice = navArgs.price
        binding.textPrimaryPrice.text = navArgs.price.toString()
        binding.textPrimaryPrice.text = getString(R.string.primary_price, navArgs.price.toString())
        binding.textTotalPrice.text = getString(R.string.total_price, totalPrice.toString())

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

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

        binding.etColor.addTextChangedListener(myTextWatcherColor)
        binding.etEngineType.addTextChangedListener(myTextWatcherEngineType)
        binding.etRemoteControl.addTextChangedListener(myTextWatcherRemoteControl)
        binding.etSoundproof.addTextChangedListener(myTextWatcherSoundproof)
//        binding.etSunroof.addTextChangedListener(myTextWatcher)
        binding.etTinting.addTextChangedListener(myTextWatcherTinting)
        binding.etVideoRecorder.addTextChangedListener(myTextWatcherVideoRecorder)
        binding.etWheelDisk.addTextChangedListener(myTextWatcherWheelDisk)

        binding.btnContinue.setOnClickListener {

            order.carId = navArgs.carId
            order.primaryPrice = navArgs.price
            order.totalPrice = navArgs.price

            if (binding.etColor.text.toString().isNotEmpty()) {
                order.color = binding.etColor.text.toString()
            } else {
                order.color = "WHITE"
            }

            if (binding.etEngineType.text.toString().isNotEmpty()) {
                order.engineType = binding.etEngineType.text.toString()
            } else {
                order.engineType = "MANUAL"
            }

            if (binding.etRemoteControl.text.toString().isNotEmpty()) {
                order.remoteController = binding.etRemoteControl.text.toString() == "INSTALLED"
            } else {
                order.remoteController = false
            }

            if (binding.etSoundproof.text.toString().isNotEmpty()) {
                order.soundproof = binding.etSoundproof.text.toString() == "INSTALLED"
            } else {
                order.soundproof = false
            }

            if (binding.etSunroof.text.toString().isNotEmpty()) {
                order.sunroof = binding.etSunroof.text.toString() == "INSTALLED"
            } else {
                order.sunroof = false
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

            order.totalPrice = totalPrice

            findNavController().navigate(
                PlaceOrderScreenDirections.actionPlaceOrderScreenToCalculatePriceScreen(order)
            )

        }
    }

    val myTextWatcherColor = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            if (binding.etColor.text.toString() != "WHITE" && !colorState) {
                totalPrice = (totalPrice + order.primaryPrice!! * 0.01).toLong()
                colorState = true
            }
            if (binding.etColor.text.toString() == "WHITE" && colorState) {
                totalPrice = (totalPrice - order.primaryPrice!! * 0.01).toLong()
                colorState = false
            }
            binding.textTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
        }
    }

    val myTextWatcherEngineType = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            if (binding.etEngineType.text.toString() == EngineType.AUTOMATIC.name && !engineTypeState) {
                totalPrice = (totalPrice + order.primaryPrice!! * 0.01).toLong()
                engineTypeState = true
            }
            if (binding.etEngineType.text.toString() == EngineType.MANUAL.name && engineTypeState) {
                totalPrice = (totalPrice - order.primaryPrice!! * 0.01).toLong()
                engineTypeState = false
            }
            binding.textTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
        }
    }

    val myTextWatcherRemoteControl = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            if (binding.etRemoteControl.text.toString() == RemoteControl.INSTALLED.name && !remoteControllerState) {
                totalPrice = (totalPrice + order.primaryPrice!! * 0.005).toLong()
                remoteControllerState = true
            }
            if (binding.etRemoteControl.text.toString() == RemoteControl.NOT_INSTALLED.name && remoteControllerState) {
                totalPrice = (totalPrice - order.primaryPrice!! * 0.005).toLong()
                remoteControllerState = false
            }
            binding.textTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
        }
    }

    val myTextWatcherSoundproof = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            if (binding.etSoundproof.text.toString() == Soundproof.INSTALLED.name && !soundproofState) {
                totalPrice = (totalPrice + order.primaryPrice!! * 0.01).toLong()
                soundproofState = true
            }
            if (binding.etSoundproof.text.toString() == Soundproof.NOT_INSTALLED.name && soundproofState) {
                totalPrice = (totalPrice - order.primaryPrice!! * 0.01).toLong()
                soundproofState = false
            }
            binding.textTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
        }
    }

    val myTextWatcherTinting = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            if (binding.etTinting.text.toString() == Tinting.INSTALLED.name && !tintingState) {
                totalPrice = (totalPrice + order.primaryPrice!! * 0.002).toLong()
                tintingState = true
            }
            if (binding.etTinting.text.toString() == Tinting.NOT_INSTALLED.name && tintingState) {
                totalPrice = (totalPrice - order.primaryPrice!! * 0.002).toLong()
                tintingState = false
            }
            binding.textTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
        }
    }

    val myTextWatcherVideoRecorder = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            if (binding.etVideoRecorder.text.toString() == VideoRecorder.INSTALLED.name && !videoRecorderState) {
                totalPrice = (totalPrice + order.primaryPrice!! * 0.001).toLong()
                videoRecorderState = true
            }
            if (binding.etVideoRecorder.text.toString() == VideoRecorder.NOT_INSTALLED.name && videoRecorderState) {
                totalPrice = (totalPrice - order.primaryPrice!! * 0.001).toLong()
                videoRecorderState = false
            }
            binding.textTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
        }
    }

    val myTextWatcherWheelDisk = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            if (binding.etWheelDisk.text.toString() == WheelDisk.CHANGED.name && !wheelDiskState) {
                totalPrice = (totalPrice + order.primaryPrice!! * 0.01).toLong()
                wheelDiskState = true
            }
            if (binding.etWheelDisk.text.toString() == WheelDisk.NOT_CHANGED.name && wheelDiskState) {
                totalPrice = (totalPrice - order.primaryPrice!! * 0.01).toLong()
                wheelDiskState = false
            }
//            binding.textTotalPrice.text = "Total Price: $${totalPrice.toString()}"
            binding.textTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
        }
    }

}