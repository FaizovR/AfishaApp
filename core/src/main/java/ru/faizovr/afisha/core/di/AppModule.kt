package ru.faizovr.afisha.core.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesContext(application: Application): Context =
        application

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources =
        application.resources

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .create()
}