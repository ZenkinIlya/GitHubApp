package com.example.githubapp.repositories.repositories

import com.example.githubapp.data.dao.AppDatabase
import com.example.githubapp.data.network.GithubApiService
import com.example.githubapp.models.db.UserRepositoryCrossRef
import com.example.githubapp.models.db.user.UserDbWithRepositoriesDb
import com.example.githubapp.models.mappers.RepositoryMapper
import com.example.githubapp.models.mappers.UserMapper
import com.example.githubapp.models.mappers.UserWithRepositoryMapper
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.models.user.User
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.internal.operators.flowable.FlowableInternalHelper
import timber.log.Timber

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
        return Single.just(mapSearchData)
            .map { it["q"].toString().isNotBlank() }
            .flatMap { flag ->
                if (flag) {
                    githubApiService.getRepositories(mapSearchData)
                        .map { it.items }
                } else {
                    Single.just(emptyList())
                }
            }
            .doOnSuccess { Timber.d("getRepositoriesFromGithubApiService(): repositories \"$mapSearchData\" from API = ${it.size} ${it.map { rep -> rep.id }}") }
        //TODO save in cacheRepository
    }

    /** Save repository which marked as favorite*/
    fun saveRepositoryInDatabase(user: User, repository: Repository): Completable {
        val userDb = userMapper.fromUser(user)
        val repositoryDb = repositoryMapper.fromRepository(repository)
        val userRepositoryCrossRef = UserRepositoryCrossRef(
            email = userDb.email,
            idRepository = repositoryDb.idRepository
        )
        Timber.d(
            "saveRepositoryInDatabase(): email = " +
                    "${userRepositoryCrossRef.email}, idRepository = ${userRepositoryCrossRef.idRepository}"
        )

        return appDatabase.getRepositoriesDao().insertUser(userDb)
            .andThen(appDatabase.getRepositoriesDao().insertRepository(repositoryDb))
            .andThen(
                appDatabase.getRepositoriesDao()
                    .insertUserRepositoryCrossRef(userRepositoryCrossRef)
            )
    }

    /** get count entries from CrossRef and load from database all favorite repositories */
    fun getSavedRepositoriesFromDatabase(user: User): Observable<List<Repository>> {
        return appDatabase.getRepositoriesDao().getCountUserRepositoryCrossRefListener(user.email)
            .doOnNext { Timber.d("getSavedRepositoriesFromDatabase(): #1 count UserRepositoryCrossRef by ${user.email} = $it") }
            .toObservable()
            .switchMap { count ->
                if (count > 0) {
                    appDatabase.getRepositoriesDao().getUserWithRepositories(user.email)
                        .doOnSuccess { Timber.d("getSavedRepositoriesFromDatabase(): #2 saved repositories by ${user.email} = ${it.repositoriesDb.size}") }
                        .map {
                            userWithRepositoryMapper.getListRepositoriesFromUserDbWithRepositoriesDb(
                                it
                            )
                        }
                        .map { listSavedRepositories ->
                            listSavedRepositories.forEach { it.favorite = true }
                            return@map listSavedRepositories
                        }
                        .toObservable()
                } else {
                    Observable.just(emptyList())
                }
            }
    }

    /** Delete saved repository which was saved by current user*/
    fun deleteSavedRepositoryByCurrentUser(user: User, repository: Repository): Completable {
        return appDatabase.getRepositoriesDao().getListUserRepositoryCrossRef()
            .doOnSuccess { Timber.d("deleteSavedRepositoryByCurrentUser(): all listUserRepositoryCrossRef by ${user.email} = $it") }
            .flatMap { listUserRepositoryCrossRef ->
                Observable.fromIterable(
                    listUserRepositoryCrossRef
                )
                    .map { it.idRepository }
                    .toList()
            }
            .doOnSuccess { Timber.d("deleteSavedRepositoryByCurrentUser(): all id repositories from crossRef = ${it.sorted()}") }
            .doOnSuccess { Timber.d("deleteSavedRepositoryByCurrentUser(): delete id repository = ${repository.id}") }
            .map { listRepositoryId ->
                listRepositoryId.count { it == repository.id } == 1
            }
            .flatMapCompletable {
                Timber.d("deleteSavedRepositoryByCurrentUser(): can we delete from repository table = $it")
                if (it) appDatabase.getRepositoriesDao()
                    .deleteRepository(repositoryMapper.fromRepository(repository))
                else Completable.complete()
            }
            .andThen(
                appDatabase.getRepositoriesDao()
                    .deleteUserRepositoryCrossRef(UserRepositoryCrossRef(user.email, repository.id))
            )
            .doOnComplete {
                Timber.d(
                    "deleteSavedRepositoryByCurrentUser(): deleted repository = ${
                        repositoryMapper.fromRepository(
                            repository
                        )
                    }"
                )
            }
    }

    /** Delete all saved repositories which was saved by current user*/
    fun deleteSavedRepositoriesByCurrentUser(user: User): Completable {
        return appDatabase.getRepositoriesDao().getCountUserRepositoryCrossRef(user.email)
            .doOnSuccess { Timber.d("deleteSavedRepositoriesByCurrentUser(): count UserRepositoryCrossRef by ${user.email} = $it") }
            .flatMap { count ->
                if (count > 0) {
                    appDatabase.getRepositoriesDao().getListUserRepositoryCrossRef()
                        .doOnSuccess { Timber.d("deleteSavedRepositoriesByCurrentUser(): all listUserRepositoryCrossRef = ${it.size}") }
                        .flatMap { listUserRepositoryCrossRef ->
                            Single.just(listUserRepositoryCrossRef.map { it.idRepository })
                        }
                } else {
                    Single.just(emptyList())
                }
            }
            .doOnSuccess { Timber.d("deleteSavedRepositoriesByCurrentUser(): all id repositories from crossRef = $it") }
            .flatMap { listIdRepositoryFromCrossRef ->
                appDatabase.getRepositoriesDao().getUserWithRepositories(user.email)
                    .map { userDbWithRepositoriesDb ->
                        userDbWithRepositoriesDb.repositoriesDb
                            .filter { repositoryDb -> listIdRepositoryFromCrossRef.count { it == repositoryDb.idRepository } == 1 }
                    }
            }
            .doOnSuccess { Timber.d("deleteSavedRepositoriesByCurrentUser(): list repository for delete = ${it.map { rep -> rep.idRepository }}") }
            .flatMapCompletable { listRepositoryDb ->
                appDatabase.getRepositoriesDao().deleteListRepository(listRepositoryDb)
            }
            .andThen(appDatabase.getRepositoriesDao().deleteUserRepositoryCrossRef(user.email))
    }

    /** Delete all tables of database. Analog: appDatabase.clearAllTables()*/
    fun deleteAllSavedRepositories(): Completable {
        return appDatabase.getRepositoriesDao().deleteAllUsers()
            .andThen(appDatabase.getRepositoriesDao().deleteAllUserRepositoryCrossRef())
            .andThen(appDatabase.getRepositoriesDao().deleteAllRepositories())
    }
}