package com.mymain.appcertificacao.codelab.userinterface.recyclerview.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

/**
 * * Classe de repositório que trabalha com fontes de dados locais e remotas.
 */
class GithubRepository(private val service: GithubService) {

    /**
     * Pesquise repositórios cujos nomes correspondam à consulta, expostos como um fluxo de dados que emitirá
     * cada vez que obtemos mais dados da rede.
     */
    fun getSearchResultStream(query: String) : Flow<PagingData<Repo>> {
        Timber.d("GithubRepository New query: $query")
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}