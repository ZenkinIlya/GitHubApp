package com.example.githubapp.data

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SignInGoogleHandler(val context: Context) {

    private var getContent: ActivityResultLauncher<GoogleSignInClient>? = null
    private var googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun initActivityResultLauncher(getContent: ActivityResultLauncher<GoogleSignInClient>){
        this.getContent = getContent
    }

    fun isClientSigned(): Boolean{
        return GoogleSignIn.getLastSignedInAccount(context) != null
    }

    fun tryToSignInGoogle() {
        getContent?.launch(googleSignInClient)
    }

    fun signOut(){
        googleSignInClient.signOut()
    }
}