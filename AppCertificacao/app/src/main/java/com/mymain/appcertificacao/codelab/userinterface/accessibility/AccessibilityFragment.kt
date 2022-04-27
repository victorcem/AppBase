package com.mymain.appcertificacao.codelab.userinterface.accessibility

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.databinding.FragmentAccessibilityBinding


class AccessibilityFragment : Fragment(R.layout.fragment_accessibility) {
    private lateinit var binding: FragmentAccessibilityBinding
    private var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccessibilityBinding.bind(view)

        binding.btnAdd.setOnClickListener {
            count ++
            binding.textAdd.text = "Apertou $count vez!"
        }
    }

}