package com.mymain.appcertificacao

import android.os.Bundle
import android.view.View
import com.mymain.appcertificacao.codelab.util.navTo
import com.mymain.appcertificacao.databinding.FragmentMainBinding

class MainFragment : androidx.fragment.app.Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // +-------------------------------------------------------------------------------------+
        // | Orientação do app: Portrait(em pé) or Landscape(deitado) or Unspecified (os dois)   |
        // +-------------------------------------------------------------------------------------+
        //se você não definir no manifest, também poderá fazê-lo dessa maneira programaticamente
        //requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = FragmentMainBinding.bind(view)
        binding.codelabToastSnake.setOnClickListener { navTo(R.id.toastSnakeFragment) }
        binding.codelabNotification.setOnClickListener { navTo(R.id.notificationFragment) }
        binding.codelabWorkManager.setOnClickListener { navTo(R.id.selectImageFragment) }
        binding.codelabMaterialComponents.setOnClickListener { navTo(R.id.materialComponentsFragment) }
        binding.codelabInteractiveUi.setOnClickListener { navTo(R.id.interactiveUiFragment) }
        binding.codelabActivitiesIntents.setOnClickListener { navTo(R.id.sendFragment) }
        binding.codelabRecyclerview.setOnClickListener { navTo(R.id.recyclerViewFragment) }
        binding.codelabRecyclerviewWithPaging.setOnClickListener { navTo(R.id.recyclerViewPagingFragment) }
        binding.codelabAccessibility.setOnClickListener { navTo(R.id.accessibilityFragment) }
        binding.codelabUserNavigationTab.setOnClickListener { navTo(R.id.tabHostFragment) }
        binding.codelabCreateCustomView.setOnClickListener { navTo(R.id.customViewFragment) }
        binding.codelabUserNavigationDrawer.setOnClickListener { navTo(R.id.drawerFragment) }
        binding.codelabThemesTouches.setOnClickListener { navTo(R.id.themeFragment) }
        binding.codelabMenuPickers.setOnClickListener { navTo(R.id.menuFragment) }
        binding.codelabThemesTouchesBaterry.setOnClickListener { navTo(R.id.batteryFragment) }
        binding.codelabRoomWithView.setOnClickListener { navTo(R.id.wordFragment) }
    }
}