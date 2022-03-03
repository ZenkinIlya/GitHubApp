package com.example.githubapp.di

import android.content.Context
import com.example.githubapp.di.application.AppComponent
import com.example.githubapp.di.application.AppModule
import com.example.githubapp.di.application.DaggerAppComponent
import com.example.githubapp.di.application.GoogleModule

class ComponentManager(val context: Context) {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}
