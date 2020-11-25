package ru.faizovr.afisha.data.remote.callback

import ru.faizovr.afisha.domain.model.Category

interface CategoriesCallback {
    fun onCategoryDataLoaded(data: List<Category>?)
    fun onError()
}