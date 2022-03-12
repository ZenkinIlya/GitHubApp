package com.example.githubapp.models.mappers

import com.example.githubapp.models.db.repository.OwnerDb
import com.example.githubapp.models.db.repository.RepositoryDb
import com.example.githubapp.models.repository.Owner
import com.example.githubapp.models.repository.Repository

class RepositoryMapper {

    fun toRepository(repositoryDb: RepositoryDb): Repository = Repository(
        id = repositoryDb.idRepository,
        name = repositoryDb.name,
        owner = Owner(
            author = repositoryDb.ownerDb.login,
            avatar = repositoryDb.ownerDb.avatar_url
        ),
        description = repositoryDb.description,
        forks_count = repositoryDb.forksCount,
        stars_count = repositoryDb.stargazersCount,
        dateOfCreation = repositoryDb.createdAt,
        favorite = false
    )

    fun fromRepository(repository: Repository): RepositoryDb = RepositoryDb(
        repositoryId = 0,
        idRepository = repository.id,
        name = repository.name,
        ownerDb = OwnerDb(
            login = repository.owner.author,
            avatar_url = repository.owner.avatar
        ),
        description = repository.description,
        forksCount = repository.forks_count,
        stargazersCount = repository.stars_count,
        createdAt = repository.dateOfCreation
    )
}