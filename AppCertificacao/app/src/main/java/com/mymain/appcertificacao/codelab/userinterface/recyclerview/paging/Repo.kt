package com.mymain.appcertificacao.codelab.userinterface.recyclerview.paging

import com.google.gson.annotations.SerializedName

/**
 * Classe de modelo imutável para um repositório Github que contém todas as informações sobre um repositório.
 * Objetos deste tipo são recebidos da API do Github, portanto todos os campos são anotados
 * com o nome serializado.
 * Esta classe também define a tabela de repositórios Room, onde o repositório [id] é a chave primária.
 */
data class Repo(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("full_name") val fullname: String,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("html_url") val url: String,
    @field:SerializedName("stargazers_count") val stars: Int,
    @field:SerializedName("forks_count") val forks: Int,
    @field:SerializedName("language") val language: String?
)