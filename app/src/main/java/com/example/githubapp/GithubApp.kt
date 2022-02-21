package com.example.githubapp

import android.app.Application
import android.content.Context
import com.example.githubapp.di.ComponentManager

class GithubApp : Application() {

    lateinit var componentManager: ComponentManager

    override fun onCreate() {
        super.onCreate()
        initComponentManager()
    }

    private fun initComponentManager() {
        componentManager = ComponentManager(this)
    }
}

val Context.componentManager: ComponentManager
    get() = when (this) {
        is GithubApp -> componentManager
        else -> this.applicationContext.componentManager
    }