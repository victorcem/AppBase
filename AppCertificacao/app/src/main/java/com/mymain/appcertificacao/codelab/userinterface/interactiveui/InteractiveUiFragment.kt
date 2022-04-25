package com.mymain.appcertificacao.codelab.userinterface.interactiveui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.codelab.util.toast
import com.mymain.appcertificacao.codelab.util.toastInt
import com.mymain.appcertificacao.databinding.FragmentInteractiveUiBinding

class InteractiveUiFragment : Fragment(R.layout.fragment_interactive_ui) {
   private lateinit var binding: FragmentInteractiveUiBinding
   private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED // Para a orientação da tela
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInteractiveUiBinding.bind(view)
        binding.showCount.text = count.toString()
        binding.buttonToast.setOnClickListener { showToast() }
        binding.buttonCount.setOnClickListener { countUp(); showCounter() }
    }

    private fun showToast() = toastInt(R.string.toast_message)
    //private fun showToast() = Toast.makeText(requireContext(), R.string.toast_message, Toast.LENGTH_SHORT).show() - essa forma usada sem a extenção criada no FragmentExt
    private fun countUp() = count++
    private fun showCounter() {
        binding.showCount.text = count.toString()
    }
}