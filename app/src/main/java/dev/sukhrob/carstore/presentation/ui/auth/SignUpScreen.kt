package dev.sukhrob.carstore.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.data.datastore.UiSource
import dev.sukhrob.carstore.databinding.ScreenSignUpBinding
import dev.sukhrob.carstore.domain.model.Manager
import dev.sukhrob.carstore.utils.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpScreen : Fragment(R.layout.screen_sign_up) {

    private val binding by viewBinding(ScreenSignUpBinding::bind)
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var uiSource: UiSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        firestore = Firebase.firestore

        binding.btnSignUp.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                binding.editTextEmail.text.toString().trim(),
                binding.editTextPassword.text.toString().trim()
            )
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        firestore.collection("managers").document(user!!.uid).set(
                            Manager(
                                user.uid,
                                binding.editTextFullname.text.toString().trim(),
                                user.email.toString().trim()
                            )
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                lifecycleScope.launch {
                                    uiSource.setJustInstalled(false)
                                }
                                findNavController().navigate(SignUpScreenDirections.actionSignUpScreenToSignInScreen())
                            } else {

                            }
                        }
                    } else {
                        // fucked
                    }
                }
        }

    }

}