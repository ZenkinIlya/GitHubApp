package com.example.githubapp.models.mappers

import com.example.githubapp.models.db.user.UserDbWithRepositoriesDb
import com.example.githubapp.models.db.user.UserDbWithRepositoryDb
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.models.user.User
import javax.inject.Inject
import kotlin.streams.toList

class UserWithRepositoryMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val repositoryMapper: RepositoryMapper
) {

    fun toUserDbWithRepositoryDb(user: User, repository: Repository): UserDbWithRepositoryDb =
        UserDbWithRepositoryDb(
            userDb = userMapper.fromUser(user),
            repositoryDb = repositoryMapper.fromRepository(repository)
        )

    fun getListRepositoriesFromUserDbWithRepositoriesDb(userDbWithRepositoriesDb: UserDbWithRepositoriesDb): List<Repository>{
        val repositoriesDb = userDbWithRepositoriesDb.repositoriesDb
        return repositoriesDb.stream()
            .map { repositoryMapper.toRepository(it) }
            .toList()
    }
}