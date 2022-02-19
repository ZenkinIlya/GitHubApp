package com.example.githubapp.presentation.login

import android.widget.Toast
import com.example.githubapp.data.SignInByGoogleContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

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
