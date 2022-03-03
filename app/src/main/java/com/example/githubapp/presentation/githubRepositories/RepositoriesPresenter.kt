package com.example.githubapp.presentation.githubRepositories

import com.example.githubapp.business.google.SignInInteractor
import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.data.Const.DEFAULT_EMAIL
import com.example.githubapp.presentation.common.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RepositoriesPresenter @Inject constructor(
    var signInInteractor: SignInInteractor,
    var repositoryInteractor: RepositoryInteractor
) :
    BasePresenter<RepositoriesView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val emailAccount = signInInteractor.getEmailAccount()
        viewState.displaySavedRepositories(emailAccount != DEFAULT_EMAIL)
    }
}