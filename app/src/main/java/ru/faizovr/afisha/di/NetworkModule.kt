package ru.faizovr.afisha.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.faizovr.afisha.data.mapper.ApiMapper
import ru.faizovr.afisha.data.remote.KudaGoApi
import ru.faizovr.afisha.data.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideKudaGoApi(retrofit: Retrofit): KudaGoApi =
        retrofit.create(KudaGoApi::class.java)

    @Provides
    @Singleton
    fun provideApiMapper(): ApiMapper =
        ApiMapper()

    @Provides
    @Singleton
    fun provideRepository(api: KudaGoApi, mapper: ApiMapper): Repository =
        Repository(api, mapper)
}