package com.example.githubapp.models.db.repository

import androidx.room.*

@Entity(
    tableName = "repositories",
    indices = [
        Index("id_repository", unique = true)
    ]
)
data class RepositoryDb(
    @PrimaryKey(autoGenerate = true)
    val repositoryId: Long,
    @ColumnInfo(name = "id_repository")
    val idRepository: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @Embedded
    val ownerDb: OwnerDb,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "forks_count")
    val forksCount: Int?,
    @ColumnInfo(name = "stargazers_count")
    val stargazersCount: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: String
)
