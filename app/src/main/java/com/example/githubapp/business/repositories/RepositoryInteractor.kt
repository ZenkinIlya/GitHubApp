package com.example.githubapp.business.repositories

import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.common.SchedulersProvider
import com.example.githubapp.repositories.repositories.RepositoriesRepository
import com.example.githubapp.repositories.user.UserRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class RepositoryInteractor(
    private val repositoriesRepository: RepositoriesRepository,
    private val schedulersProvider: SchedulersProvider,
    private val userRepository: UserRepository
) {

    /** Get all repositories by searchData and saved repositories from database.
     * Check if listSavedRep contains repositories from githubApi */
    fun getRepositories(mapSearchData: Map<String, String>): Observable<List<Repository>> {
        return Observable.zip(
            repositoriesRepository.getRepositoriesFromGithubApiService(mapSearchData)
                .flatMapObservable { Observable.fromIterable(it) },
            getSavedRepositories().toObservable()
        ) { repFromApi, listSavedRep ->
            repFromApi.favorite = listSavedRep.contains(repFromApi)
            return@zip repFromApi
        }
            .toList()
            .toObservable()
    }

    /** Save repository which marked as favorite*/
    fun saveRepository(repository: Repository): Single<Long> {
        return repositoriesRepository.saveRepositoryInDatabase(
            userRepository.getUser(),
            repository
        ).subscribeOn(schedulersProvider.io())
    }

    /** Get all saved repositories*/
    fun getSavedRepositories(): Single<List<Repository>> {
        return repositoriesRepository.getSavedRepositoriesFromDatabase(userRepository.getUser())
            .subscribeOn(schedulersProvider.io())
    }

    /** Delete saved repositories*/
    fun deleteSavedRepositories(): Single<Long> {
        return repositoriesRepository.deleteSavedRepositoriesByCurrentUser(userRepository.getUser())
            .subscribeOn(schedulersProvider.io())
    }

    /** Delete choose saved repository*/
    fun deleteSavedRepository(repository: Repository): Single<Long> {
        return repositoriesRepository.deleteSavedRepositoryByCurrentUser(
            userRepository.getUser(),
            repository
        )
            .subscribeOn(schedulersProvider.io())
    }

    /** Delete all saved repositories by all users*/
    fun deleteSavedRepositoriesByAllUsers(): Single<Long> {
        return repositoriesRepository.deleteAllSavedRepositories()
            .subscribeOn(schedulersProvider.io())
    }
}