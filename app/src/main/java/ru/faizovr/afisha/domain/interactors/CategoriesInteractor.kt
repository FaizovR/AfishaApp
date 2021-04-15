package ru.faizovr.afisha.domain.interactors

import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.domain.model.Category

class CategoriesInteractor(
    val repository: Repository
) {
    suspend fun getCategoriesList(): Lce<List<Category>> =
        repository.getCategoriesList()
}