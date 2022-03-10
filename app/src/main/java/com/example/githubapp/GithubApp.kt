package com.example.githubapp

import android.app.Application
import android.content.Context
import com.example.githubapp.di.ComponentManager
import timber.log.Timber
import timber.log.Timber.Forest.plant

class GithubApp : Application() {

    lateinit var componentManager: ComponentManager

    override fun onCreate() {
        super.onCreate()
        initComponentManager()
        initLogging()
    }

    private fun initComponentManager() {
        componentManager = ComponentManager(this)
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
    }
}

val Context.componentManager: ComponentManager
    get() = when (this) {
        is GithubApp -> componentManager
        else -> this.applicationContext.componentManager
    }
