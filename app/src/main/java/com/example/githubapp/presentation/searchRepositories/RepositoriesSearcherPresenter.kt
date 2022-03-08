package com.example.githubapp.presentation.searchRepositories

import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.presentation.common.BasePresenter
import com.example.githubapp.presentation.common.SchedulersProvider
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class RepositoriesSearcherPresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
    private val schedulersProvider: SchedulersProvider
) :
    BasePresenter<RepositoriesSearcherView>() {

    fun onSearchRepositories(mapSearchData: Map<String, String>) {
        val disposable: Disposable =
            repositoryInteractor.getRepositoriesWithFavoriteFromCache(mapSearchData)
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
}