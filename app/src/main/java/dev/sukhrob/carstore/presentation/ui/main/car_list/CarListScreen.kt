package dev.sukhrob.carstore.presentation.ui.main.car_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ScreenCarListBinding
import dev.sukhrob.carstore.presentation.ui.main.MainVM
import dev.sukhrob.carstore.presentation.ui.main.car_list.adapters.CarListAdapter

class CarListScreen: Fragment(R.layout.screen_car_list) {

    private val viewModel: MainVM by viewModels()
    private val binding by viewBinding(ScreenCarListBinding::bind)
    private lateinit var adapter: CarListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progress.visibility = View.VISIBLE

        lifecycleScope.launchWhenStarted {
            viewModel.carList.collect {
                if (it.isNotEmpty()) {
                    adapter = CarListAdapter(it)
                    adapter.listener = { cardId, price ->
                        Log.d("magiccc", price.toString())
                        findNavController().navigate(CarListScreenDirections.actionCarListScreenToPlaceOrderScreen(cardId, price))
                    }
                    setupRecyclerView()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.progress.visibility = View.GONE
        binding.rvCarList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCarList.adapter = this.adapter
    }

}