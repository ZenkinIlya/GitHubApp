package com.example.githubapp.presentation.githubRepositories

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RepositoriesView: MvpView {

    fun showLoading(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(error: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun displayViewPageRepositories(showPageSavedRepositories: Boolean)
}