package com.example.githubapp.repositories.repositories

import com.example.githubapp.data.dao.AppDatabase
import com.example.githubapp.data.network.GithubApiService
import com.example.githubapp.models.db.UserRepositoryCrossRef
import com.example.githubapp.models.mappers.RepositoryMapper
import com.example.githubapp.models.mappers.UserMapper
import com.example.githubapp.models.mappers.UserWithRepositoryMapper
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.models.user.User
import io.reactivex.rxjava3.core.Single
import kotlin.streams.toList

class RepositoriesRepository(
    private val githubApiService: GithubApiService,
    private val cacheRepository: CacheRepository,
    private val appDatabase: AppDatabase,
    private val userMapper: UserMapper,
    private val repositoryMapper: RepositoryMapper,
    private val userWithRepositoryMapper: UserWithRepositoryMapper
) {

    /** Get repositories from githubApi service with save in cache*/
    fun getRepositoriesFromGithubApiService(mapSearchData: Map<String, String>): Single<List<Repository>> {
        return githubApiService.getRepositories(mapSearchData)
            .map { it.items }
            .doOnSuccess { repositoryEntities ->
                //TODO Create save in cache loaded repositories
/*                cacheRepository.clearCache()
                cacheRepository.putRepositories(repositoryEntities)*/
            }
    }

    /** Save repository which marked as favorite*/
    fun saveRepositoryInDatabase(user: User, repository: Repository): Single<Long> {
        return Single.create {
            val userDb = userMapper.fromUser(user)
            val repositoryDb = repositoryMapper.fromRepository(repository)
            val userRepositoryCrossRef = UserRepositoryCrossRef(
                email = userDb.email,
                idRepository = repositoryDb.idRepository
            )
            appDatabase.getRepositoriesDao().insertUser(userDb)
            appDatabase.getRepositoriesDao().insertRepository(repositoryDb)
            appDatabase.getRepositoriesDao().insertUserRepositoryCrossRef(userRepositoryCrossRef)
        }
    }

    /** Load from database all favorite repositories*/
    fun getSavedRepositoriesFromDatabase(user: User): Single<List<Repository>> {
        return Single.create {
            val userWithRepositories =
                appDatabase.getRepositoriesDao().getUserWithRepositories(user.email)
            userWithRepositoryMapper.getListRepositoriesFromUserDbWithRepositoriesDb(userWithRepositories)
        }
    }

    /** Delete all saved repositories which was saved by current user*/
    fun deleteSavedRepositoriesByCurrentUser(user: User): Single<Long> {
        return Single.create {
            //Get all idRepository from CrossRef
            val listIdRepositoryFromListUserRepositoryCrossRef =
                appDatabase.getRepositoriesDao().getListUserRepositoryCrossRef().map { it.idRepository }.toList()

            //Get all repositories which will be deleted
            val repositoriesDbWhichWillBeDeleted =
                appDatabase.getRepositoriesDao().getUserWithRepositories(user.email).repositoriesDb.stream()
                    .filter { element -> listIdRepositoryFromListUserRepositoryCrossRef.count { it == element.idRepository } == 1 }
                    .toList()

            appDatabase.getRepositoriesDao().deleteUserRepositoryCrossRef(user.email)
            appDatabase.getRepositoriesDao().deleteListRepository(repositoriesDbWhichWillBeDeleted)
        }
    }

    /** Delete saved repository which was saved by current user*/
    fun deleteSavedRepositoryByCurrentUser(user: User, repository: Repository): Single<Long> {
        return Single.create {
            //Get all idRepository from CrossRef
            val listIdRepositoryFromListUserRepositoryCrossRef =
                appDatabase.getRepositoriesDao().getListUserRepositoryCrossRef().map { it.idRepository }.toList()

            //Check how much crossRef contains the repository.id and delete if count contains == 1
            if (listIdRepositoryFromListUserRepositoryCrossRef.count { it == repository.id } == 1){
                appDatabase.getRepositoriesDao().deleteRepository(repositoryMapper.fromRepository(repository))
            }
            appDatabase.getRepositoriesDao().deleteUserRepositoryCrossRef(UserRepositoryCrossRef(user.email, repository.id))
        }
    }

    /** Delete all tables of database. Analog: appDatabase.clearAllTables()*/
    fun deleteAllSavedRepositories(): Single<Long> {
        return Single.create {
            appDatabase.getRepositoriesDao().deleteAllUsers()
            appDatabase.getRepositoriesDao().deleteAllUserRepositoryCrossRef()
            appDatabase.getRepositoriesDao().deleteAllRepositories()
        }
    }
}