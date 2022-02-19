package com.example.githubapp.presentation.login

interface LoginView {

    fun showLoading(show: Boolean)
    fun showError(error: String)
    fun letTheUserIn()
    fun letTheUnknownUserIn()
    fun signInGoogle()

}
