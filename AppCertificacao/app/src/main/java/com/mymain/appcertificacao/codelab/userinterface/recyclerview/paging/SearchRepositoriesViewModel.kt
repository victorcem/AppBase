package com.mymain.appcertificacao.codelab.userinterface.recyclerview.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel para a tela [SearchRepositoriesActivity].
 * O ViewModel trabalha com o [GithubRepository] para obter os dados.
 */
class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<Repo>> {
        // Se ja existir algo no cache para a mesma busca, apenas retorne o resultado
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) return lastResult

        currentQueryValue = queryString
        val newResult: Flow<PagingData<Repo>> =  repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult // Cache/salve na memory
        return  newResult
    }
}