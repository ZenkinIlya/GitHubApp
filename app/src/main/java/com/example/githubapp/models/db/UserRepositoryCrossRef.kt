package com.example.githubapp.models.db

import androidx.room.Entity

@Entity(primaryKeys = ["email", "idRepository"])
data class UserRepositoryCrossRef(
    val email: String,
    val idRepository: Long
)
