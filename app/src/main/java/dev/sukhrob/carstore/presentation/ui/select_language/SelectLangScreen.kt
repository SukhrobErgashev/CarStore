package dev.sukhrob.carstore.presentation.ui.select_language

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.carstore.MainActivity
import dev.sukhrob.carstore.R
import dev.sukhrob.carstore.databinding.ScreenSelectLangBinding
import java.util.*

@AndroidEntryPoint
class SelectLangScreen: Fragment(R.layout.screen_select_lang){

    private val binding by viewBinding(ScreenSelectLangBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Locale.getDefault().displayLanguage == "English") {
            binding.btnEnglish.setBackgroundResource(R.drawable.shape_select_lang)
            binding.btnRussian.setBackgroundResource(R.drawable.shape_unselect_lang)
        } else {
            binding.btnRussian.setBackgroundResource(R.drawable.shape_select_lang)
            binding.btnEnglish.setBackgroundResource(R.drawable.shape_unselect_lang)
        }

        binding.btnEnglish.setOnClickListener {
            (activity as? MainActivity)?.updateLocale(Locale("en"))
        }

        binding.btnRussian.setOnClickListener {
            (activity as? MainActivity)?.updateLocale(Locale("ru"))
        }

        binding.btnEnter.setOnClickListener {
            findNavController().navigate(SelectLangScreenDirections.actionSelectLangScreenToSignInScreen())
        }

    }

}