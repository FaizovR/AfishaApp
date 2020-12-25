package ru.faizovr.afisha.data

import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventList

interface Repository {

    suspend fun getCategoriesList(): Result<List<Category>>

    suspend fun getEventList(page: String, category: Category): EventList?
}