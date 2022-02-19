package com.example.githubapp

import android.app.Application
import com.example.githubapp.di.ComponentManager

class GithubApp: Application() {

    lateinit var componentManager: ComponentManager

    override fun onCreate() {
        super.onCreate()
        componentManager = ComponentManager(this)
        componentManager.getAppComponent()
    }
}