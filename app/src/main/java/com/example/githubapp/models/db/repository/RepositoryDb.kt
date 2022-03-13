package com.example.githubapp.models.db.repository

import androidx.room.*
import com.example.githubapp.models.repository.Owner

@Entity(
    tableName = "repositories",
)
data class RepositoryDb(
    @PrimaryKey
    val idRepository: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @Embedded
    val owner: Owner,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "forks_count")
    val forksCount: Int?,
    @ColumnInfo(name = "stargazers_count")
    val stargazersCount: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: String
)
