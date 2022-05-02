package com.example.githubapp.models.api

import android.os.Parcelable
import com.example.githubapp.models.repository.Owner
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryApi(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("description")
    val description: String?,
    @SerializedName("forks_count")
    val forks_count: Int?,
    @SerializedName("stargazers_count")
    val stars_count: Int?,
    @SerializedName("created_at")
    val dateOfCreation: String,

    var favorite: Boolean

): Parcelable
