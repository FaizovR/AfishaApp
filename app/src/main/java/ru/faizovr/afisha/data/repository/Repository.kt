package ru.faizovr.afisha.data.repository

import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventList

interface Repository {

    suspend fun getCategoriesList(): Result<List<Category>>

    suspend fun getEventList(page: String, category: Category): EventList?
}