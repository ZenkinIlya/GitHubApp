package com.example.githubapp.models.mappers

import com.example.githubapp.models.db.repository.RepositoryDb
import com.example.githubapp.models.repository.Repository

class RepositoryMapper {

    fun toRepository(repositoryDb: RepositoryDb): Repository = Repository(
        id = repositoryDb.idRepository,
        name = repositoryDb.name,
        owner = repositoryDb.owner,
        description = repositoryDb.description,
        forks_count = repositoryDb.forksCount,
        stars_count = repositoryDb.stargazersCount,
        dateOfCreation = repositoryDb.createdAt,
        favorite = false
    )

    fun fromRepository(repository: Repository): RepositoryDb = RepositoryDb(
        idRepository = repository.id,
        name = repository.name,
        owner = repository.owner,
        description = repository.description,
        forksCount = repository.forks_count,
        stargazersCount = repository.stars_count,
        createdAt = repository.dateOfCreation
    )
}