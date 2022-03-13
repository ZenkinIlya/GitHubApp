package com.example.githubapp.business.google

import com.example.githubapp.data.Const.DEFAULT_DISPLAY_NAME
import com.example.githubapp.data.Const.DEFAULT_EMAIL
import com.example.githubapp.data.Const.DEFAULT_ID_TOKEN
import com.example.githubapp.data.Const.DEFAULT_PHOTO_URL
import com.example.githubapp.models.user.User
import com.example.githubapp.repositories.user.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class SignInInteractor(private val userRepository: UserRepository) {

    fun saveSignInGoogleDataAccount(googleSignInAccount: GoogleSignInAccount) {
        userRepository.putUser(user = User(
            email = googleSignInAccount.email ?: DEFAULT_EMAIL,
            displayName = googleSignInAccount.displayName ?: DEFAULT_DISPLAY_NAME,
            idToken = googleSignInAccount.idToken ?: DEFAULT_ID_TOKEN,
            photoUrl = googleSignInAccount.photoUrl.toString()
        ))
    }

    fun getCurrentUser(): User{
        return userRepository.getUser()
    }

    fun saveDefaultDataAccount() {
        userRepository.putUser(user = User(
            email = DEFAULT_EMAIL,
            displayName = DEFAULT_DISPLAY_NAME,
            idToken = DEFAULT_ID_TOKEN,
            photoUrl = DEFAULT_PHOTO_URL
        ))
    }
}