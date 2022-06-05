package com.mymain.appcertificacao.codelab.userinterface.usernavigation.tab

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.databinding.FragmentTabThreeBinding

class TabThreeFragment(val viewModel: TabViewModel) : Fragment(R.layout.fragment_tab_three) {

    private lateinit var binding: FragmentTabThreeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTabThreeBinding.bind(view)

        // Criar um observador que atualiza a UI
        val tabContentObserver = Observer<String> { newContent ->
            binding.contentTitle.text = viewModel.getCurrent(newContent)
        }

        viewModel.getContentObserver().observe(viewLifecycleOwner, tabContentObserver)

        binding.btnTabThree.setOnClickListener {
            viewModel.getContentObserver().setValue("Tab Three!")
        }
    }
}