package dev.sukhrob.carstore.presentation.ui.main.car_list.calculate_price

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ScreenCalculatePriceBinding
import dev.sukhrob.carstore.presentation.ui.main.MainVM

class CalculatePriceScreen : Fragment(R.layout.screen_calculate_price) {

    private val binding by viewBinding(ScreenCalculatePriceBinding::bind)
    private val viewModel: CalculatePriceVM by viewModels()
    private val mainViewModel: MainVM by viewModels()
    private val navArgs: CalculatePriceScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textBasePrice.text = navArgs.order.totalPrice.toString()

        observePayByCash()
        observeTotalPrice()
        setSeekbarListeners()
        observeDiscount()

        binding.paymentMethod.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.optionCredit.id) {
                viewModel.setPayByCash(false)
            } else {
                viewModel.setPayByCash(true)
            }
        }

        binding.btnOrder.setOnClickListener {
//            val order = navArgs.order.apply {
//                this.totalPrice = viewModel.totalPrice.value
//            }
//            mainViewModel.insertOrder(order)
            gotoCustomerInfoScreen()
        }
    }

    private fun gotoCustomerInfoScreen() {
        findNavController().navigate(
            CalculatePriceScreenDirections.actionCalculatePriceScreenToCustomerInfoScreen(
                navArgs.order.copy(totalPrice = viewModel.totalPrice.value)
            )
        )
    }

    private fun observeTotalPrice() {
        lifecycleScope.launchWhenStarted {
            viewModel.totalPrice.collect {
                binding.textTotalPrice.text = it.toString()
            }
        }
    }

    private fun observeDiscount() {
        lifecycleScope.launchWhenStarted {
            viewModel.discount.collect {
                binding.textDiscountCounter.text = it.toString()
                viewModel.calculateTotalPrice(navArgs.order.totalPrice!!)
            }
        }
    }

    private fun setSeekbarListeners() {
        binding.seekbarCount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.seekbarCount.min = 1
                binding.seekbarCount.max = 10
                viewModel.setDiscount(progress)
                binding.textCounter.text = progress.toString()
                viewModel.setDiscount(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.seekbarPeriod.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.seekbarPeriod.min = 3
                binding.seekbarPeriod.max = 60
                viewModel.setLoanDuration(progress)
                binding.textPeriod.text = progress.toString()
                viewModel.calculateTotalPrice(navArgs.order.totalPrice!!)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun observePayByCash() {
        lifecycleScope.launchWhenStarted {
            viewModel.payByCash.collect {
                if (it) {
                    binding.constraintLayout.visibility = View.GONE
                } else {
                    binding.constraintLayout.visibility = View.VISIBLE
                }
            }
        }
    }

}