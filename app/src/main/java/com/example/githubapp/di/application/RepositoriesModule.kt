package com.example.githubapp.di.application

import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.data.network.GithubApiService
import com.example.githubapp.presentation.common.SchedulersProvider
import com.example.githubapp.repositories.repositories.RepositoriesCache
import com.example.githubapp.repositories.repositories.RepositoriesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideRepositoriesInteractor(
        repositoriesRepository: RepositoriesRepository,
        schedulersProvider: SchedulersProvider
    ): RepositoryInteractor {
        return RepositoryInteractor(repositoriesRepository, schedulersProvider)
    }

    @Provides
    @Singleton
    fun provideRepositoriesRepository(
        githubApiService: GithubApiService,
        repositoriesCache: RepositoriesCache
    ): RepositoriesRepository {
        return RepositoriesRepository(githubApiService, repositoriesCache)
    }

    @Provides
    @Singleton
    fun provideRepositoriesCache(): RepositoriesCache {
        return RepositoriesCache()
    }
}