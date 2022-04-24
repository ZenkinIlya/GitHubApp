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

    fun onGetFavoriteRepositories(mapSearchData: Map<String, String>){
        val disposable: Disposable =
            repositoryInteractor.getSavedRepositories(mapSearchData)
                .observeOn(schedulersProvider.ui())
                .doOnSubscribe { viewState.showLoading(true) }
                .subscribe(
                    { repositoryList ->
                        Timber.i("saved repositories retrieved")
                        viewState.showRepositories(repositoryList)
                        viewState.showLoading(false)
                    },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable, 1)
    }

    fun onClickFavorite(repository: Repository) {
        deleteRepository(repository)
    }

    private fun deleteRepository(repository: Repository) {
        val disposable: Disposable =
            repositoryInteractor.deleteSavedRepository(repository)
                .observeOn(schedulersProvider.ui())
                .subscribe({
                    viewState.removeRepositoryFromFavorite(repository)
                    Timber.i("deleted repository completed")
                },
                    { t -> viewState.showError(t.localizedMessage) })
        unsubscribeOnDestroy(disposable, 7)
    }

}