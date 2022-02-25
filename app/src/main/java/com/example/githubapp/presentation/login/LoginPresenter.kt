package com.example.githubapp.presentation.login

import com.example.githubapp.presentation.common.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LoginPresenter
@Inject
constructor(): BasePresenter<LoginView>() {

    fun onClickSignInGoogle(){
        viewState.showLoading(true)
        viewState.signInGoogle()
        viewState.showLoading(false)
    }

    fun onClickSignInGithub() {
        //TODO Create Github authorization
    }
}
