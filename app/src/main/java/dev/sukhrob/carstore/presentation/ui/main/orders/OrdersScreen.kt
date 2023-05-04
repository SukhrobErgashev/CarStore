package dev.sukhrob.carstore.presentation.ui.main.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ScreenOrdersBinding
import dev.sukhrob.carstore.presentation.ui.main.MainVM
import dev.sukhrob.carstore.presentation.ui.main.car_list.CarListScreenDirections
import dev.sukhrob.carstore.presentation.ui.main.car_list.adapters.CarListAdapter

class OrdersScreen: Fragment(R.layout.screen_orders) {

    private val viewModel: MainVM by viewModels()
    private val binding by viewBinding(ScreenOrdersBinding::bind)
    private lateinit var adapter: OrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOrders()

        lifecycleScope.launchWhenStarted {
            viewModel.orders.collect {
                if (it.isNotEmpty()) {
                    adapter = OrdersAdapter(it)
                    setupRecyclerView()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvOrdersList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrdersList.adapter = this.adapter
    }

}