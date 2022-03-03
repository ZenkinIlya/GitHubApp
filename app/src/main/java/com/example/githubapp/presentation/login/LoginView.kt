package com.example.githubapp.presentation.login

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * AddToEndStrategy – добавит пришедшую команду в конец очереди. Используется по умолчанию
 * AddToEndSingleStrategy – добавит пришедшую команду в конец очереди команд. Причём, если
 *      команда такого типа уже есть в очереди, то уже существующая будет удалена
 * SingleStateStrategy – очистит всю очередь команд, после чего добавит себя в неё
 * SkipStrategy – команда не будет добавлена в очередь, и никак не изменит очередь
 * OneExecutionStateStrategy - команда будет сохранена в очереди команд, но будет удалена после ее первого выполнения
 */

//@StateStrategyType – аннотация для управления стратегией добавления и удаления команды из очереди команд во ViewState
@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : MvpView {

    fun showLoading(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(error: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorSignInGoogle(statusMessage: Int?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToRepositories()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun signInGoogle()

}
