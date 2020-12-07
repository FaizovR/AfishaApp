package ru.faizovr.afisha.data

import ru.faizovr.afisha.data.remote.callback.CategoriesCallback
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventList

interface Repository {

    fun getCategoryFromApi(callback: CategoriesCallback)

    suspend fun getEventList(page: String, category: Category): EventList?
}