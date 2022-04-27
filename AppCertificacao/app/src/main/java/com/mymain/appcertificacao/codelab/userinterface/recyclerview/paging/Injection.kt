package com.mymain.appcertificacao.codelab.userinterface.recyclerview.paging

import androidx.lifecycle.ViewModelProvider

/**
 * Classe que trata da criação de objetos.
 * Assim, objetos podem ser passados como parâmetros nos construtores e então substituídos por
 * teste, quando necessário.
 */

object Injection {

    /**
     * Cria uma instância de [GithubRepository] com base no [GithubService] e um
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(): GithubRepository {
        return GithubRepository(GithubService.create())
    }

    /**
     * Fornece o [ViewModelProvider.Factory] que é usado para obter uma referência a
     * Objetos [ViewModel].
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}