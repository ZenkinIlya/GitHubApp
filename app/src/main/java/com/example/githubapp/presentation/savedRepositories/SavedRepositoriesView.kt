package com.example.githubapp.presentation.savedRepositories

import com.example.githubapp.models.repository.Repository
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SavedRepositoriesView : MvpView {

    fun showLoading(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(error: String?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showRepositories(listRepository: List<Repository>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun updateRepositories(repositoryList: List<Repository>)
}