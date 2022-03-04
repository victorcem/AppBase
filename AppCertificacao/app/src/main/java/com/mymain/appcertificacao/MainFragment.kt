package com.mymain.appcertificacao

import android.os.Bundle
import android.view.View
import com.mymain.appcertificacao.databinding.FragmentMainBinding

/** Main Menu Study Guide **/
class MainFragment : androidx.fragment.app.Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.teste
    }
}