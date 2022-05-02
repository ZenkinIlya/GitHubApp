package com.example.githubapp.models.repository

import android.os.Parcelable
import com.example.githubapp.models.api.RepositoryApi
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoriesResponse(
    @SerializedName("items")
    val items: List<RepositoryApi>
):Parcelable