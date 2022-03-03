package com.example.githubapp.models

import java.util.*

data class Repository(
    val nameRepository: String,
    val author: String,
    val description: String,
    val forks: Int,
    val stars: Int,
    val dateOfCreation: Date
)
