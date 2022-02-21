package com.example.githubapp.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class SignInGoogleContract: ActivityResultContract<GoogleSignInClient, GoogleSignInAccount?>() {

    override fun createIntent(context: Context, input: GoogleSignInClient): Intent {
        return input.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): GoogleSignInAccount? {
        Log.i(TAG, "resultCode = $resultCode; intent = $intent")
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null

        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
        return handleSignInResult(task)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>): GoogleSignInAccount? {
        return try {
            task.getResult(ApiException::class.java)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.statusCode)
            null
        }
    }

    companion object{
        const val TAG = "tagSignInGoogleContract"
    }

}