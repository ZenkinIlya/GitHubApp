package com.example.githubapp.di.application

import com.example.githubapp.presentation.login.LoginFragment
import com.example.githubapp.presentation.login.LoginPresenter
import com.example.githubapp.presentation.repositoriesList.ListRepositoryFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, GoogleModule::class])
@Singleton
interface AppComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(listRepositoryFragment: ListRepositoryFragment)
}
