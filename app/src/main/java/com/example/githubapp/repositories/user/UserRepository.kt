package com.example.githubapp.repositories.user

import android.content.SharedPreferences
import com.example.githubapp.data.Const.USER
import com.example.githubapp.models.user.User
import com.google.gson.Gson

class UserRepository(private val sharedPreferences: SharedPreferences) {

    fun putUser(user: User) {
        val gson = Gson()
        val jsonUser = gson.toJson(user)
        sharedPreferences.edit()
            .putString(USER, jsonUser)
            .apply()
    }

    fun getUser(): User{
        val gson = Gson()
        val json = sharedPreferences.getString(USER, "")
        return gson.fromJson(json, User::class.java)
    }
}