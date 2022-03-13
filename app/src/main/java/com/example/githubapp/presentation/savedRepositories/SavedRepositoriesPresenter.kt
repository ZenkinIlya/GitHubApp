package com.example.githubapp.presentation.savedRepositories

import com.example.githubapp.business.repositories.RepositoryInteractor
import com.example.githubapp.presentation.common.BasePresenter
import com.example.githubapp.presentation.common.SchedulersProvider
import javax.inject.Inject

class SavedRepositoriesPresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
    private val schedulersProvider: SchedulersProvider
) : BasePresenter<SavedRepositoriesView>() {

    fun onSearchSavedRepositories(nameRepository: String?) {
    }

}