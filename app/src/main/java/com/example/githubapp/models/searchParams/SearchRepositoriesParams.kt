package com.example.githubapp.models.searchParams

data class SearchRepositoriesParams(
    val q: String,
    val sort: String? = null,
    val order: String? = null,
    val per_page: Int? = null,
    val page: Int? = null
)