package com.example.githubapp.models.repository

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login")
    val author: String?,
    @SerializedName("avatar_url")
    val avatar: String?)
