package com.example.githubapp.di.application

import android.content.SharedPreferences
import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.data.dao.AppDatabase
import com.example.githubapp.data.network.GetRepositoriesRxRepository
import com.example.githubapp.data.network.GithubApiService
import com.example.githubapp.data.network.PageSource
import com.example.githubapp.models.mappers.RepositoryMapper
import com.example.githubapp.models.mappers.UserMapper
import com.example.githubapp.models.mappers.UserWithRepositoryMapper
import com.example.githubapp.presentation.common.SchedulersProvider
import com.example.githubapp.repositories.repositories.CacheRepository
import com.example.githubapp.repositories.repositories.RepositoriesRepository
import com.example.githubapp.repositories.user.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideRepositoriesInteractor(
        repositoriesRepository: RepositoriesRepository,
        cacheRepository: CacheRepository,
        schedulersProvider: SchedulersProvider,
        userRepository: UserRepository
    ): RepositoryInteractor {
        return RepositoryInteractor(repositoriesRepository, cacheRepository, schedulersProvider, userRepository)
    }

    @Provides
    @Singleton
    fun provideRepositoriesRepository(
        githubApiService: GithubApiService,
        appDatabase: AppDatabase,
        userMapper: UserMapper,
        repositoryMapper: RepositoryMapper,
        userWithRepositoryMapper: UserWithRepositoryMapper
    ): RepositoriesRepository {
        return RepositoriesRepository(
            githubApiService,
            appDatabase,
            userMapper,
            repositoryMapper,
            userWithRepositoryMapper
        )
    }

    @Provides
    @Singleton
    fun provideRepositoriesCache(sharedPreferences: SharedPreferences): CacheRepository {
        return CacheRepository(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRepositoriesRxRepository(pagingSource: PageSource.Factory): GetRepositoriesRxRepository {
        return GetRepositoriesRxRepository.GetRepositoriesRxRepositoryImpl(pagingSource)
    }
}