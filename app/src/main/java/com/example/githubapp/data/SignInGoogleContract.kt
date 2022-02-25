package com.example.githubapp.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.example.githubapp.models.SignInGoogleWrapper
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class SignInGoogleContract : ActivityResultContract<GoogleSignInClient, SignInGoogleWrapper?>() {

    override fun createIntent(context: Context, input: GoogleSignInClient): Intent {
        return input.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): SignInGoogleWrapper? {
        Log.i(TAG, "resultCode = $resultCode; intent = $intent")

        if (intent == null) return null
        val signInResultFromIntent = Auth.GoogleSignInApi.getSignInResultFromIntent(intent)
        val statusCode = signInResultFromIntent?.status?.statusCode
        Log.i(TAG, "statusCode = $statusCode")

        val signInGoogleWrapper = SignInGoogleWrapper(null, statusCode)

        if (resultCode != Activity.RESULT_OK) return signInGoogleWrapper

        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
        return handleSignInResult(task, signInGoogleWrapper)
    }

    private fun handleSignInResult(
        task: Task<GoogleSignInAccount>,
        signInGoogleWrapper: SignInGoogleWrapper
    ): SignInGoogleWrapper? {
        return try {
            val result = task.getResult(ApiException::class.java)

            signInGoogleWrapper.googleSignInAccount = result
            signInGoogleWrapper
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            signInGoogleWrapper.statusCode = e.statusCode
            Log.e(TAG, "handleSignInResult() : statusCode = ${signInGoogleWrapper.statusCode}")
            signInGoogleWrapper
        }
    }

    companion object {
        const val TAG = "tagSignInGoogleContract"
    }

}