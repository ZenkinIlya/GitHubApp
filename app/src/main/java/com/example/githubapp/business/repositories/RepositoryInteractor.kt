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
            Timber.d("getRepositories(): #5 repositoriesFromApi = ${repositoriesFromApi.size} ${repositoriesFromApi.map { it.id }}")
            Timber.d("getRepositories(): #6 savedRepositories = ${savedRepositories.size} ${savedRepositories.map { it.id }}")
            markRepositoriesAsFavorite(repositoriesFromApi, savedRepositories)
        }.subscribeOn(schedulersProvider.io())
    }

    /** Mark repositories as favorite which contains in second param*/
    private fun markRepositoriesAsFavorite(
        repositories: List<Repository>,
        favoriteRepositories: List<Repository>
    ): List<Repository> {
        return repositories.map { repository ->
            repository.favorite = favoriteRepositories.any { it.id == repository.id }
/*                Timber.d("markRepositoriesAsFavorite(): repository: ${repository.id}, " +
                        "name = ${repository.name}, " +
                        "favorite = ${repository.favorite}, " +
                        "ref = ${System.identityHashCode(repository)}")*/
            return@map repository
        }
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
            .doOnNext { Timber.d("getSavedRepositories(): #3 saved repositories by ${userRepository.getUser().email} = ${it.size}") }
            .switchMap { listSavedRepositories ->
                if (!mapSearchData["q"].isNullOrBlank()){
                    Observable.fromIterable(listSavedRepositories)
                        .filter { repository -> repository.name.contains(mapSearchData["q"].toString(), ignoreCase = true) }
                        .toList()
                        .toObservable()
                }else{
                    Observable.just(listSavedRepositories)
                }
            }
            .doOnNext { Timber.d("getSavedRepositories(): #4 filtered saved repositories by ${userRepository.getUser().email} = ${it.size}") }
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