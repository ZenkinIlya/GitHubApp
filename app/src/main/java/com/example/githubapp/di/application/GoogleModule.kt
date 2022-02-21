package com.example.githubapp.di.application

import android.content.Context
import com.example.githubapp.data.SignInGoogleHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GoogleModule(context: Context) {

    private var context: Context = context.applicationContext

    @Provides
    @Singleton
    fun provideContext(): Context{
        return context
    }

    @Provides
    @Singleton
    fun provideSignInGoogleHandler(context: Context): SignInGoogleHandler{
        return SignInGoogleHandler(context)
    }

}
