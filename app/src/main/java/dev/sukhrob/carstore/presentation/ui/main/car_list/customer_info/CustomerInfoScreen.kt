package dev.sukhrob.carstore.presentation.ui.main.car_list.customer_info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ScreenCalculatePriceBinding
import dev.sukhrob.carstore.databinding.ScreenCustomerInfoBinding
import dev.sukhrob.carstore.presentation.ui.main.MainVM
import dev.sukhrob.carstore.presentation.ui.main.car_list.calculate_price.CalculatePriceScreenArgs
import dev.sukhrob.carstore.presentation.ui.main.car_list.calculate_price.CalculatePriceVM

class CustomerInfoScreen : Fragment(R.layout.screen_customer_info) {

    private val binding by viewBinding(ScreenCustomerInfoBinding::bind)
    private val mainViewModel: MainVM by viewModels()
    private val navArgs: CustomerInfoScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            gotoCarListScreen()
            binding.progress.visibility = View.GONE
        }
    }

    private fun gotoCarListScreen() {
        mainViewModel.insertOrder(
            navArgs.order.copy(
                customerFullName = "${binding.editTextName.text} ${binding.editTextSurname.text}",
                customerPhoneNumber = binding.editTextPhoneNumber.text.toString()
            )
        )
        findNavController().navigate(CustomerInfoScreenDirections.actionCustomerInfoScreenToCarListScreen())
    }

}