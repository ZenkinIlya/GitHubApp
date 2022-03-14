package com.example.githubapp.presentation.searchRepositories

import com.example.githubapp.models.repository.Repository
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RepositoriesSearcherView: MvpView {

    fun showLoading(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(error: String?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showRepositories(listRepository: List<Repository>)
}
