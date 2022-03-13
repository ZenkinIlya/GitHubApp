package com.example.githubapp.di.application

import com.example.githubapp.models.mappers.RepositoryMapper
import com.example.githubapp.models.mappers.UserMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MappersModule {

    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper{
        return UserMapper()
    }

    @Provides
    @Singleton
    fun provideRepositoryMapper(): RepositoryMapper {
        return RepositoryMapper()
    }
}