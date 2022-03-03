package com.example.githubapp.di.application

import android.content.Context
import android.content.SharedPreferences
import com.example.githubapp.business.google.SignInInteractor
import com.example.githubapp.data.signIn.SignInGoogleHandler
import com.example.githubapp.repositories.user.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GoogleModule {

    @Provides
    @Singleton
    fun provideSignInGoogleHandler(context: Context): SignInGoogleHandler {
        return SignInGoogleHandler(context)
    }

    @Provides
    @Singleton
    fun provideSignInGoogleInteractor(userRepository: UserRepository): SignInInteractor {
        return SignInInteractor(userRepository)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        sharedPreferences: SharedPreferences
    ): UserRepository {
        return UserRepository(sharedPreferences)
    }

}
