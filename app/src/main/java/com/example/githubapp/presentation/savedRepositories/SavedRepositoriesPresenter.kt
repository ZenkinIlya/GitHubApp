package com.example.githubapp.presentation.savedRepositories

import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.presentation.common.BasePresenter
import com.example.githubapp.presentation.common.SchedulersProvider
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class SavedRepositoriesPresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
    private val schedulersProvider: SchedulersProvider
) : BasePresenter<SavedRepositoriesView>() {

    fun onSearchSavedRepositories(nameRepository: String?) {
    }

    fun onGetFavoriteRepositories(){
        val disposable: Disposable =
            repositoryInteractor.getSavedRepositories()
                .observeOn(schedulersProvider.ui())
                .doOnSubscribe { viewState.showLoading(true) }
                .subscribe(
                    { repositoryList ->
                        viewState.showRepositories(repositoryList)
                        viewState.showLoading(false)
                    },
                    { t -> viewState.showError(t.localizedMessage) },
                    { Timber.i("repositoryInteractor.getSavedRepositories() completed")})
        unsubscribeOnDestroy(disposable, 1)
    }

}