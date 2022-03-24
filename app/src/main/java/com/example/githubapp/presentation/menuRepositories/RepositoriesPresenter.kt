package com.example.githubapp.presentation.menuRepositories

import com.example.githubapp.business.google.SignInInteractor
import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.data.Const.DEFAULT_EMAIL
import com.example.githubapp.presentation.common.BasePresenter
import com.example.githubapp.presentation.common.SchedulersProvider
import io.reactivex.rxjava3.disposables.Disposable
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class RepositoriesPresenter @Inject constructor(
    private var signInInteractor: SignInInteractor,
    var repositoryInteractor: RepositoryInteractor,
    var schedulersProvider: SchedulersProvider
) :
    BasePresenter<RepositoriesView>() {

    fun init() {
        val currentUser = signInInteractor.getCurrentUser()
        viewState.displayViewPageRepositories(currentUser.email != DEFAULT_EMAIL)
    }

    fun onDeleteSavedRepositories() {
        val disposable: Disposable =
            repositoryInteractor.deleteSavedRepositories()
                .observeOn(schedulersProvider.ui())
                .subscribe(
                    { Timber.i("Complete delete saved repositories") },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable, 2)
    }

    fun onDeleteSavedRepositoriesByAllUsers() {
        val disposable: Disposable =
            repositoryInteractor.deleteSavedRepositoriesByAllUsers()
                .observeOn(schedulersProvider.ui())
                .subscribe(
                    { Timber.i("Complete delete saved repositories by all users") },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable, 3)
    }
}