package com.example.githubapp.business.google

import com.example.githubapp.data.Const.DEFAULT_EMAIL
import com.example.githubapp.repositories.user.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class SignInInteractor(private val userRepository: UserRepository) {

    fun saveSignInGoogleDataAccount(googleSignInAccount: GoogleSignInAccount?) {
        userRepository.putEmail(googleSignInAccount?.email)
    }

    fun getEmailAccount(): String{
        return userRepository.getEmail()
    }

    fun saveDefaultDataAccount() {
        userRepository.putEmail(DEFAULT_EMAIL)
    }
}