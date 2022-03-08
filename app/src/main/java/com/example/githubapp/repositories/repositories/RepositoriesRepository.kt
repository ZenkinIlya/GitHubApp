package com.example.githubapp.repositories.repositories

import com.example.githubapp.data.network.GithubApiService
import com.example.githubapp.models.repository.Repository
import io.reactivex.rxjava3.core.Single

class RepositoriesRepository(
    private val githubApiService: GithubApiService,
    private val repositoriesCache: RepositoriesCache
) {

    /** Get repositories from githubApi service with save in cache*/
    fun getRepositoriesFromService(mapSearchData: Map<String, String>): Single<List<Repository>> {
        return githubApiService.getRepositories(mapSearchData)
            .map { it.items }
            .doOnSuccess { repositoryEntities ->
                repositoriesCache.clearCache()
                repositoriesCache.putRepositories(repositoryEntities)
            }
    }

    /** Save repository which marked as favorite*/
    fun saveFavoriteRepositoryInCache(repository: Repository) {

    }

    /** Load from cache all favorite repositories*/
    fun getFavoriteRepositoriesFromCache(): List<Repository> {
        TODO("Not yet implemented")
    }
}