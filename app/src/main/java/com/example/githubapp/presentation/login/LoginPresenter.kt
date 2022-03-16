package com.example.githubapp.presentation.login

import com.example.githubapp.business.google.SignInInteractor
import com.example.githubapp.models.signIn.SignInGoogleWrapper
import com.example.githubapp.presentation.common.BasePresenter
import com.google.android.gms.common.api.CommonStatusCodes
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(private val signInInteractor: SignInInteractor) :
    BasePresenter<LoginView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //Сообщает в каком состоянии находится view. Например позволяет понять нужна ли анимация или нет
        isInRestoreState(viewState)
    }

    fun onClickSignInGoogle() {
        viewState.showLoading(true)
        viewState.signInGoogle()
        viewState.showLoading(false)
    }

    fun onClickSignInGithub() {
        //TODO Create Github authorization
    }

    fun onGetSignInGoogleWrapperFromContract(signInGoogleWrapper: SignInGoogleWrapper?) {
        if (signInGoogleWrapper == null) {
            viewState.showError("Unknown error")
        } else {
            when (signInGoogleWrapper.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val googleSignInAccount = signInGoogleWrapper.googleSignInAccount
                    if (googleSignInAccount == null) {
                        viewState.showError("statusCode SUCCESS but googleSignInAccount null")
                    } else {
                        signInInteractor.saveSignInGoogleDataAccount(googleSignInAccount)
                        viewState.navigateToRepositories()
                    }
                }
                else ->
                    viewState.showErrorSignInGoogle(signInGoogleWrapper.statusCode)
            }
        }
    }

    fun onClickSignInWithoutAuth() {
        signInInteractor.saveDefaultDataAccount()
        viewState.navigateToRepositories()
    }
}
