package com.example.githubapp.business.repositories

import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.common.SchedulersProvider
import com.example.githubapp.repositories.repositories.RepositoriesRepository
import com.example.githubapp.repositories.user.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class RepositoryInteractor(
    private val repositoriesRepository: RepositoriesRepository,
    private val schedulersProvider: SchedulersProvider,
    private val userRepository: UserRepository
) {

    /** Get all repositories by searchData and saved repositories from database.
     * Check if listSavedRep contains repositories from githubApi */
    fun getRepositories(mapSearchData: Map<String, String>): Observable<List<Repository>> {
        return Observable.combineLatest(
            repositoriesRepository.getRepositoriesFromGithubApiService(mapSearchData)
                .toObservable(),
            getSavedRepositories(mapSearchData)
        ) { repositoriesFromApi, savedRepositories ->
            Timber.d("getRepositories(): #6 repositoriesFromApi = ${repositoriesFromApi.size} ${repositoriesFromApi.map { it.id }}")
            Timber.d("getRepositories(): #7 savedRepositories = ${savedRepositories.size} ${savedRepositories.map { it.id }}")
            repositoriesFromApi.map { repositoryFromApi ->
                repositoryFromApi.favorite = savedRepositories.any { it.id == repositoryFromApi.id }
/*                Timber.d("getRepositories(): repositoryFromApi: ${repositoryFromApi.id}, " +
                        "name = ${repositoryFromApi.name}, " +
                        "favorite = ${repositoryFromApi.favorite}, " +
                        "ref = ${System.identityHashCode(repositoryFromApi)}")*/
                return@map repositoryFromApi
            }
        }.subscribeOn(schedulersProvider.io())
    }

    /** Save repository which marked as favorite*/
    fun saveRepository(repository: Repository): Completable {
        return repositoriesRepository.saveRepositoryInDatabase(
            userRepository.getUser(),
            repository
        ).subscribeOn(schedulersProvider.io())
    }

    /** Get saved repositories by current user*/
    fun getSavedRepositories(mapSearchData: Map<String, String>): Observable<List<Repository>> {
        return repositoriesRepository.getSavedRepositoriesFromDatabase(userRepository.getUser())
            .doOnNext { Timber.d("getSavedRepositories(): #4 saved repositories by ${userRepository.getUser().email} = ${it.size}") }
            .map { listSavedRepositories ->
                listSavedRepositories.filter { repository ->
                    return@filter repository.name.contains(mapSearchData["q"].toString(), ignoreCase = true)
                }
            }
            .doOnNext { Timber.d("getSavedRepositories(): #5 filtered saved repositories by ${userRepository.getUser().email} = ${it.size}") }
            .doOnError { t -> Timber.e("getSavedRepositories: ${t.localizedMessage}") }
            .subscribeOn(schedulersProvider.io())
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
}