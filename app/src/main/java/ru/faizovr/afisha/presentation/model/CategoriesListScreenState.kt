package ru.faizovr.afisha.presentation.model

import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.core.presentation.models.RefreshableScreenState

class CategoriesListScreenState(
    override val lce: Lce<List<CategoryDataView>>? = null
) : RefreshableScreenState<List<CategoryDataView>> {
    val isCategoryListVisible: Boolean = isLoadedSuccessfully()
    val categories = lce?.content
}