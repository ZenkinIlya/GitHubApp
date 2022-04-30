package com.example.githubapp.models.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoriesResponse(
    @SerializedName("items")
    val items: List<Repository>
):Parcelable