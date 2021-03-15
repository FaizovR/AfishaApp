package ru.faizovr.afisha.domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.domain.model.Category

class CategoriesInteractor(
    val repository: Repository
) {
    suspend fun getCategoriesList(): Flow<Lce<List<Category>>> =
        repository.getCategoriesList()
}