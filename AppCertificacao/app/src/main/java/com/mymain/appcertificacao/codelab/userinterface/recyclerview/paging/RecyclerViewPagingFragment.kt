package com.mymain.appcertificacao.codelab.userinterface.recyclerview.paging

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.databinding.FragmentReyclerViewPagingBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

// https://developer.android.com/topic/libraries/architecture/paging/v3-overview
// https://developer.android.com/codelabs/android-paging?index=..%2F..%2Findex#4
class RecyclerViewPagingFragment : Fragment(R.layout.fragment_reycler_view_paging) {

    private lateinit var binding: FragmentReyclerViewPagingBinding
    private lateinit var viewModel: SearchRepositoriesViewModel
    private val adapter = ReposAdapter()

    private var searchJob: Job? = null

    private fun search(query: String) {
        // Certifique-se de cancelar o trabalho anterior antes de criar um novo
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collect {
                adapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReyclerViewPagingBinding.bind(view)

        // Obter o modelo de visualização
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(SearchRepositoriesViewModel::class.java)

        // Adicionar divisores entre os itens de linha do RecyclerView
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)
    }

    private fun initAdapter() {
        binding.list.adapter = adapter
    }

    private fun initSearch(query: String) {
        binding.searchRepo.setText(query)

        binding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        binding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        // Role para o topo quando a lista for atualizada da rede.
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Emitir apenas quando o REFRESH LoadState for RemoteMediator for alterado.
                .distinctUntilChangedBy { it.refresh }
                // Reaja apenas aos casos em que a REFRESH Remota for concluída, ou seja, Não Carregando.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.list.scrollToPosition(0) }
        }
    }

    private fun updateRepoListFromInput() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if(show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }

}