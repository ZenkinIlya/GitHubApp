package com.example.githubapp.presentation.login

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView: MvpView {

    fun showLoading(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(error: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun letTheUserIn()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun letTheUnknownUserIn()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun signInGoogle()

}
