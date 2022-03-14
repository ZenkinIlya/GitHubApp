package com.example.githubapp.di.application

import com.example.githubapp.presentation.login.LoginFragment
import com.example.githubapp.presentation.menuRepositories.RepositoriesFragment
import com.example.githubapp.presentation.repository.RepositoryFragment
import com.example.githubapp.presentation.savedRepositories.SavedRepositoriesFragment
import com.example.githubapp.presentation.searchRepositories.RepositoriesSearcherFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, GoogleModule::class, RepositoriesModule::class,
    RetrofitModule::class, MappersModule::class, RoomModule::class])
@Singleton
interface AppComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(repositoriesFragment: RepositoriesFragment)

    fun inject(repositoriesSearcherFragment: RepositoriesSearcherFragment)

    fun inject(savedRepositoriesFragment: SavedRepositoriesFragment)

    fun inject(repositoryFragment: RepositoryFragment)

}
