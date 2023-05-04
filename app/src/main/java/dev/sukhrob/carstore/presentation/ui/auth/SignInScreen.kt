package dev.sukhrob.carstore.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.data.datastore.UiSource
import dev.sukhrob.carstore.databinding.ScreenSignInBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInScreen : Fragment(R.layout.screen_sign_in) {

    private val binding by viewBinding(ScreenSignInBinding::bind)
    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var uiSource: UiSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.textCreateNew.setOnClickListener {
            findNavController().navigate(SignInScreenDirections.actionSignInScreenToSignUpScreen())
        }

        binding.btnEnter.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(
                binding.editTextEmail.text.toString().trim(),
                binding.editTextPassword.text.toString().trim()
            )
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        binding.progress.visibility = View.GONE
                        lifecycleScope.launch {
                            uiSource.setJustInstalled(false)
                        }
                        gotoMainScreen()
                    } else {
                        binding.progress.visibility = View.GONE
                    }
                }
        }
    }

    private fun gotoMainScreen() {
        findNavController().navigate(SignInScreenDirections.actionSignInScreenToMainScreen())
    }

}