package com.example.githubapp.di.application

import android.content.SharedPreferences
import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.repositories.repositories.RepositoriesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideRepositoriesInteractor(repositoriesRepository: RepositoriesRepository): RepositoryInteractor {
        return RepositoryInteractor(repositoriesRepository)
    }

    @Provides
    @Singleton
    fun provideRepositoriesRepository(sharedPreferences: SharedPreferences): RepositoriesRepository {
        return RepositoriesRepository(sharedPreferences)
    }
}