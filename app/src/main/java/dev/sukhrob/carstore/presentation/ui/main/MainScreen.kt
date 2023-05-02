package dev.sukhrob.carstore.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ScreenMainBinding

@AndroidEntryPoint
class MainScreen: Fragment(R.layout.screen_main) {

    private lateinit var navController: NavController
    private val binding by viewBinding(ScreenMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(
            requireActivity(), R.id.navHostScreen
        )
        binding.bottomNavigation.setupWithNavController(navController)
    }

}