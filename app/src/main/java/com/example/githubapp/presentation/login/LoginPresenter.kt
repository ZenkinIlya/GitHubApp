package com.example.githubapp.presentation.login

class LoginPresenter(var loginView: LoginView) {

    fun onClickSignInGoogle(){
        loginView.showLoading(true)
        loginView.signInGoogle()
        loginView.showLoading(false)
    }

    fun onClickSignInGithub() {
        //TODO Create Github authorization
    }
}
