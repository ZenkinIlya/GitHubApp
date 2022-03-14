package com.example.githubapp.models.db

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "userRepositoryCrossRef",
    primaryKeys = ["email", "idRepository"],
    indices = [Index(value = ["idRepository"])]
)
data class UserRepositoryCrossRef(
    val email: String,
    val idRepository: Long
)
