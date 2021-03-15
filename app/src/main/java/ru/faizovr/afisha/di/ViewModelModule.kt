package ru.faizovr.afisha.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.faizovr.afisha.core.presentation.mapper.DateMapper
import ru.faizovr.afisha.presentation.mapper.CategoryMapper
import ru.faizovr.afisha.presentation.mapper.EventListDataViewMapper
import ru.faizovr.afisha.presentation.mapper.EventMapper

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideCategoryMapper(): CategoryMapper =
        CategoryMapper()

    @Provides
    @ViewModelScoped
    fun provideEventListDataViewMapper(dateMapper: DateMapper): EventListDataViewMapper =
        EventListDataViewMapper(dateMapper)

    @Provides
    @ViewModelScoped
    fun provideEventMapper(dateMapper: DateMapper): EventMapper =
        EventMapper(dateMapper)
}