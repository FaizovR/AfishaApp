package ru.faizovr.afisha.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.domain.interactors.CategoriesInteractor
import ru.faizovr.afisha.domain.interactors.EventsInteractor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideCategoriesInteractor(
        repository: Repository,
    ): CategoriesInteractor =
        CategoriesInteractor(repository)

    @Provides
    @Singleton
    fun provideEventInteractor(repository: Repository): EventsInteractor =
        EventsInteractor(repository)
}