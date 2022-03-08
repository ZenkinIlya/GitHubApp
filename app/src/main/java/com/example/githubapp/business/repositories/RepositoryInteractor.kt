package com.example.githubapp.business.repositories

import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.common.SchedulersProvider
import com.example.githubapp.repositories.repositories.RepositoriesRepository
import io.reactivex.rxjava3.core.Single

class RepositoryInteractor(
    private val repositoriesRepository: RepositoriesRepository,
    private val schedulersProvider: SchedulersProvider
) {

    /** Get all repositories by searchData and search favorite repositories by compare
     * with repositories from cache */
    fun getRepositoriesWithFavoriteFromCache(mapSearchData: Map<String, String>): Single<List<Repository>> {
        return repositoriesRepository.getRepositoriesFromService(mapSearchData)
            .subscribeOn(schedulersProvider.io())
    }

    /** Save repository which marked as favorite*/
    fun saveFavoriteRepository(repository: Repository) {
        repositoriesRepository.saveFavoriteRepositoryInCache(repository)
    }

    /** Get all favorite repositories*/
    fun getFavoriteRepositories(): List<Repository> {
        return repositoriesRepository.getFavoriteRepositoriesFromCache()
    }
}