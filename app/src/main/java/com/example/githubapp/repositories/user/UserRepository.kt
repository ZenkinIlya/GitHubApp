package com.example.githubapp.repositories.user

import android.content.SharedPreferences
import com.example.githubapp.data.Const.DEFAULT_EMAIL
import com.example.githubapp.data.Const.EMAIL

class UserRepository(private val sharedPreferences: SharedPreferences) {

    fun putEmail(email: String?) {
        sharedPreferences.edit()
            .putString(EMAIL, email)
            .apply()
    }

    fun getEmail(): String {
        return if (!sharedPreferences.contains(EMAIL)) {
            DEFAULT_EMAIL
        } else sharedPreferences.getString(EMAIL, DEFAULT_EMAIL).toString()
    }
}