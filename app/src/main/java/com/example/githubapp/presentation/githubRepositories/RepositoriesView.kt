package com.example.githubapp.presentation.githubRepositories

import com.example.githubapp.models.Repository
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
    fun displaySavedRepositories(flag: Boolean)
}