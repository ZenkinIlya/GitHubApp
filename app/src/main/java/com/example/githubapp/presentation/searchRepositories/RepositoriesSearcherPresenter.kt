package com.example.githubapp.presentation.searchRepositories

import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.common.BasePresenter
import com.example.githubapp.presentation.common.SchedulersProvider
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class RepositoriesSearcherPresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
    private val schedulersProvider: SchedulersProvider
) :
    BasePresenter<RepositoriesSearcherView>() {

    fun onSearchRepositories(mapSearchData: Map<String, String>) {
        val disposable: Disposable =
            repositoryInteractor.getRepositories(mapSearchData)
                .observeOn(schedulersProvider.ui())
                .doOnSubscribe { viewState.showLoading(true) }
                .subscribe(
                    { repositoryList ->
                        viewState.showRepositories(repositoryList)
                        viewState.showLoading(false)
                    },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable)
    }

    fun onClickFavorite(repository: Repository) {
        if (repository.favorite){
            saveRepository(repository)
        }else{
            deleteRepository(repository)
        }
    }

    private fun saveRepository(repository: Repository) {
        val disposable: Disposable =
            repositoryInteractor.saveRepository(repository)
                .observeOn(schedulersProvider.ui())
                .subscribe({
                    Timber.i("saved repository index=${it}")
                },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable)
    }

    private fun deleteRepository(repository: Repository) {
        val disposable: Disposable =
            repositoryInteractor.deleteSavedRepository(repository)
                .observeOn(schedulersProvider.ui())
                .subscribe({
                    Timber.i("deleted repository index=${it}")
                },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable)
    }
}