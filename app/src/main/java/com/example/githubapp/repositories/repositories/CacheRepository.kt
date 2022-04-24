package com.example.githubapp.repositories.repositories

import android.content.SharedPreferences
import com.example.githubapp.data.Const
import com.example.githubapp.data.Const.CACHE_REPOSITORIES
import com.example.githubapp.models.repository.Repository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CacheRepository(private val sharedPreferences: SharedPreferences) {

    fun clearCache() {
        val gson = Gson()
        val jsonEmptyRepositories = gson.toJson(emptyList<Repository>())
        sharedPreferences.edit()
            .putString(CACHE_REPOSITORIES, jsonEmptyRepositories)
            .apply()
    }

    fun putRepositories(listRepositories: List<Repository>) {
        val gson = Gson()
        val jsonRepositories = gson.toJson(listRepositories)
        sharedPreferences.edit()
            .putString(CACHE_REPOSITORIES, jsonRepositories)
            .apply()
    }

    fun getRepositories(): List<Repository> {
        val gson = Gson()
        val json = sharedPreferences.getString(CACHE_REPOSITORIES, "")
        val listType: Type = object : TypeToken<List<Repository>>() {}.type
        return gson.fromJson(json, listType)
    }
}