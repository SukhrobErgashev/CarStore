package dev.sukhrob.carstore.presentation.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.data.datastore.UiSource
import dev.sukhrob.carstore.databinding.ScreenSplashBinding
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen: Fragment(R.layout.screen_splash) {

    @Inject
    lateinit var uiMode: UiSource

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main) {
            uiMode.justInstalled.collect {
                delay(1000)
                gotoSignInOrSelectLangScreen(it)
            }
        }
    }

    private fun gotoSignInOrSelectLangScreen(justInstalled: Boolean) {
        if (justInstalled) {
            findNavController().navigate(SplashScreenDirections.actionSplashScreenToSelectLangScreen())
        } else {
            findNavController().navigate(SplashScreenDirections.actionSplashScreenToSignInScreen())
        }
    }

}