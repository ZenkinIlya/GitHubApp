package com.example.githubapp.models.db.repository

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "owners",
)
data class OwnerDb(
    @ColumnInfo(name = "login")
    val login: String?,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String?)
