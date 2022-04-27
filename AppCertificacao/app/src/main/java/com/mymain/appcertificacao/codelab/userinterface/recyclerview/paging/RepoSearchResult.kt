package com.mymain.appcertificacao.codelab.userinterface.recyclerview.paging

import java.lang.Exception

/**
 * RepoSearchResult de uma pesquisa, que contém List<Repo> contendo dados de consulta,
 * e uma String de estado de erro de rede.
 */
sealed class RepoSearchResult {
    data class Success(val data: List<Repo>) : RepoSearchResult()
    data class Error(val error: Exception) : RepoSearchResult()
}