package com.example.githubapp.di.application

import android.content.Context
import android.content.SharedPreferences
import com.example.githubapp.presentation.common.SchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    private var context: Context = context.applicationContext

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideSchedulersProvider(): SchedulersProvider {
        return SchedulersProvider()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
    }

    companion object {
        const val APP_PREFS = "AppPrefs"
    }
}