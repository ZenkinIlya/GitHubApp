package com.example.githubapp.business.repositories

import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.common.SchedulersProvider
import com.example.githubapp.repositories.repositories.CacheRepository
import com.example.githubapp.repositories.repositories.RepositoriesRepository
import com.example.githubapp.repositories.user.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber

class RepositoryInteractor(
    private val repositoriesRepository: RepositoriesRepository,
    private val cacheRepository: CacheRepository,
    private val schedulersProvider: SchedulersProvider,
    private val userRepository: UserRepository
) {

    /** Get all repositories from service. if repositories not empty, save them in cache.
     * Get saved repositories from database and mark some repositories from service as favorite */
    fun getRepositories(mapSearchData: Map<String, String>): Single<List<Repository>> {
        return repositoriesRepository.getRepositoriesFromGithubApiService(mapSearchData)
            .map {
                if (it.isNotEmpty()) {
                    Timber.i("getRepositories(): #2 cacheRepository clear and put")
                    cacheRepository.clearCache()
                    cacheRepository.putRepositories(it)
                }
                return@map it
            }
            .onErrorReturn { getCachedRepositories(mapSearchData) }
            .flatMap { repositories ->
                getSavedRepositories(mapSearchData).map { savedRepositories ->
                    markRepositoriesAsFavorite(repositories, savedRepositories)
                }
            }.subscribeOn(schedulersProvider.io())
    }

    /** Mark repositories as favorite which contains in second param*/
    private fun markRepositoriesAsFavorite(
        repositories: List<Repository>,
        favoriteRepositories: List<Repository>
    ): List<Repository> {
        Timber.d("getRepositories(): #8 repositoriesFromApi = ${repositories.size} ${repositories.map { it.id }}")
        Timber.d("getRepositories(): #9 savedRepositories = ${favoriteRepositories.size} ${favoriteRepositories.map { it.id }}")
        return repositories.map { repository ->
            repository.favorite = favoriteRepositories.any { it.id == repository.id }
/*                Timber.d("markRepositoriesAsFavorite(): repository: ${repository.id}, " +
                        "name = ${repository.name}, " +
                        "favorite = ${repository.favorite}, " +
                        "ref = ${System.identityHashCode(repository)}")*/
            return@map repository
        }
    }

    /** Get cached repositories*/
    private fun getCachedRepositories(mapSearchData: Map<String, String>): List<Repository> {
        val repositories = filterByMap(mapSearchData, cacheRepository.getRepositories())
        Timber.i("getCachedRepositories(): #3 repositories \"$mapSearchData\" from Cache = ${repositories.size} ${repositories.map { rep -> rep.id }}")
        return repositories
    }

    /** Save repository which marked as favorite*/
    fun saveRepository(repository: Repository): Completable {
        return repositoriesRepository.saveRepositoryInDatabase(
            userRepository.getUser(),
            repository
        ).subscribeOn(schedulersProvider.io())
    }

    /** Get saved repositories by current user*/
    fun getSavedRepositories(mapSearchData: Map<String, String>): Single<List<Repository>> {
        return repositoriesRepository.getSavedRepositoriesFromDatabase(userRepository.getUser())
            .doOnSuccess { Timber.d("getSavedRepositories(): #6 saved repositories by ${userRepository.getUser().email} = ${it.size}") }
            .map { listSavedRepositories -> filterByMap(mapSearchData, listSavedRepositories)
            }
            .doOnSuccess { Timber.d("getSavedRepositories(): #7 filtered \"$mapSearchData\" saved repositories by ${userRepository.getUser().email} = ${it.size}") }
            .doOnError { t -> Timber.e("getSavedRepositories(): ${t.localizedMessage}") }
            .subscribeOn(schedulersProvider.io())
    }

    /** Filter by map data
     * if value of key=q is empty, return input list without changes*/
    private fun filterByMap(
        mapSearchData: Map<String, String>,
        listRepositories: List<Repository>
    ): List<Repository> {
        return if (mapSearchData["q"].isNullOrBlank()) {
            listRepositories
                .filter { repository ->
                    repository.name.contains(
                        mapSearchData["q"].toString(),
                        ignoreCase = true
                    )
                }
        } else {
            listRepositories
        }
    }

    /** Delete saved repositories*/
    fun deleteSavedRepositories(): Completable {
        return repositoriesRepository.deleteSavedRepositoriesByCurrentUser(userRepository.getUser())
            .subscribeOn(schedulersProvider.io())
    }

    /** Delete choose saved repository*/
    fun deleteSavedRepository(repository: Repository): Completable {
        return repositoriesRepository.deleteSavedRepositoryByCurrentUser(
            userRepository.getUser(),
            repository
        ).subscribeOn(schedulersProvider.io())
    }

    /** Delete all saved repositories by all users*/
    fun deleteSavedRepositoriesByAllUsers(): Completable {
        return repositoriesRepository.deleteAllSavedRepositories()
            .subscribeOn(schedulersProvider.io())
    }

    fun getCurrentRepositoriesFromDatabase(): Observable<List<Repository>> {
        return repositoriesRepository.getCurrentRepositoriesFromDatabase(userRepository.getUser())
            .subscribeOn(schedulersProvider.io())
    }
}