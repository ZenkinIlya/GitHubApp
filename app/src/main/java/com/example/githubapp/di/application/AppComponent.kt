package com.example.githubapp.di.application

import com.example.githubapp.presentation.login.LoginFragment
import com.example.githubapp.presentation.githubRepositories.RepositoriesFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, GoogleModule::class, RepositoriesModule::class])
@Singleton
interface AppComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(repositoriesFragment: RepositoriesFragment)
}
