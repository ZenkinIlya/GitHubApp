package com.example.githubapp.models.repository

import com.google.gson.annotations.SerializedName

data class RepositoriesResponse(
    @SerializedName("items")
    val items: List<Repository>
)