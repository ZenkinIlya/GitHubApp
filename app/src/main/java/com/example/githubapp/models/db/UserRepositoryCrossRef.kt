package com.example.githubapp.models.db

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "repositoryId"])
data class UserRepositoryCrossRef(
    val userId: Long,
    val repositoryId: Long
)
