package com.example.githubapp.presentation.savedRepositories

import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.common.BasePresenter
import com.example.githubapp.presentation.common.SchedulersProvider
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class SavedRepositoriesPresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
    private val schedulersProvider: SchedulersProvider
) : BasePresenter<SavedRepositoriesView>() {

    fun initRepositoriesDatabaseListener() {
        val disposable: Disposable =
            repositoryInteractor.getCurrentRepositoriesFromDatabase()
                .observeOn(schedulersProvider.ui())
                .subscribe(
                    { repositoryList ->
                        viewState.updateRepositories(repositoryList)
                        Timber.i("received saved repositories and update saved page")
                    },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable, 8)
    }

    fun onGetFavoriteRepositories(mapSearchData: Map<String, String>) {
        val disposable: Disposable =
            repositoryInteractor.getSavedRepositories(mapSearchData)
                .observeOn(schedulersProvider.ui())
                .doOnSubscribe { viewState.showLoading(true) }
                .subscribe(
                    { repositoryList ->
                        Timber.i("received saved repositories")
                        viewState.showRepositories(repositoryList)
                        viewState.showLoading(false)
                    },
                    { t ->
                        viewState.showError(t.localizedMessage)
                        viewState.showLoading(false)
                    })
        unsubscribeOnDestroy(disposable, 1)
    }

    fun onClickFavorite(repository: Repository) {
        val disposable: Disposable =
            repositoryInteractor.deleteSavedRepository(repository)
                .observeOn(schedulersProvider.ui())
                .subscribe({
                    Timber.i("deleted repository")
                },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable, 7)
    }
}