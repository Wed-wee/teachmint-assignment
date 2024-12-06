package com.practice.teachmint_internship_jetpack.data.model

data class SearchResponse(
    val items: List<RepositoryItem>
)

data class RepositoryItem(
    val id: Int,
    val name: String,
    val description: String?,
    val html_url: String?,
    val owner: Owner
)

data class Owner(
    val login: String
)
