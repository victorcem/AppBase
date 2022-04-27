package com.mymain.appcertificacao.codelab.userinterface.recyclerview.paging

import com.google.gson.annotations.SerializedName


/**
 * Dataclass para reter respostas de repositório de chamadas de API searchRepo.
 */
data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null

)