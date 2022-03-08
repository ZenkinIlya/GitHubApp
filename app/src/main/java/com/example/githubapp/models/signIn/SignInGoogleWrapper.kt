package com.example.githubapp.models.signIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

data class SignInGoogleWrapper(var googleSignInAccount: GoogleSignInAccount?, var statusCode: Int?) {
}