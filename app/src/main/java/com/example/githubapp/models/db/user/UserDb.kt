package com.example.githubapp.models.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
)
data class UserDb(
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "id_token")
    val idToken: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String
)
