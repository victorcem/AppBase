package com.mymain.appcertificacao.codelab.userinterface.materialcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.codelab.util.navTo
import com.mymain.appcertificacao.databinding.FragmentMaterialComponentsBinding
import kotlinx.android.synthetic.main.fragment_material_components.*


class MaterialComponentsFragment : Fragment(R.layout.fragment_material_components) {

    private lateinit var binding: FragmentMaterialComponentsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMaterialComponentsBinding.bind(view) // Inicializa o binding

        binding.nextButton.setOnClickListener {
            if(!isPasswordValid(password_edit_text.text.toString())) {
                password_text_input.error = getString(R.string.shr_error_password)
            } else {
                password_text_input.error = null
                navTo(R.id.productGridFragment)
            }
        }

        // Clear the error once more than 8 characters are typed.
        binding.passwordEditText.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_edit_text.text.toString())) {
                password_text_input.error = null
            }
            false
        }

        binding.cancelButton.setOnClickListener {
            binding.passwordEditText.setText("")
            binding.username.setText("")
        }
    }

    private fun isPasswordValid(text:String) : Boolean = text.length >= 8
}