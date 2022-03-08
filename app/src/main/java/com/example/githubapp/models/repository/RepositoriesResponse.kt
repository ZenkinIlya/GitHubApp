package com.example.githubapp.models.repository

import com.example.githubapp.models.repository.Repository
import com.google.gson.annotations.SerializedName

data class RepositoriesResponse(
    @SerializedName("items")
    val items: List<Repository>
)