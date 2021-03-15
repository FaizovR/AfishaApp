package ru.faizovr.afisha.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.faizovr.afisha.core.presentation.mapper.DateMapper

@Module
@InstallIn(ViewModelComponent::class)
class CoreViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideDateMapper(): DateMapper =
        DateMapper()
}