package dev.sukhrob.carstore.presentation.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ScreenProfileBinding
import dev.sukhrob.carstore.presentation.ui.main.MainVM
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileScreen: Fragment(R.layout.screen_profile) {

    private val viewModel: MainVM by viewModels()
    private val binding by viewBinding(ScreenProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gerManagerInfo()

        lifecycleScope.launchWhenStarted {
            viewModel.managerInfo.collect {
                if (it.email != null) {
                    binding.textView4.text = it.fullname!!.split(' ')[0]
                    binding.textView7.text = it.fullname!!.split(' ')[1]
                    binding.textView9.text = it.email
                }
            }
        }

    }

}