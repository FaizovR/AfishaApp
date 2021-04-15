package ru.faizovr.afisha.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.core.presentation.viewModel.ScreenDataFetchingViewModel
import ru.faizovr.afisha.domain.interactors.CategoriesInteractor
import ru.faizovr.afisha.presentation.commands.CategoriesCommands
import ru.faizovr.afisha.presentation.mapper.CategoryMapper
import ru.faizovr.afisha.presentation.model.CategoriesListScreenState
import ru.faizovr.afisha.presentation.model.CategoryDataView
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val categoriesInteractor: CategoriesInteractor,
    private val categoryMapper: CategoryMapper
) : ScreenDataFetchingViewModel<List<CategoryDataView>, CategoriesListScreenState, CategoriesCommands>(
    CategoriesListScreenState()
) {

    init {
        fetchScreenData()
    }

    private fun updateScreenState(
        lce: Lce<List<CategoryDataView>>? = model.lce,
        shouldRefreshView: Boolean = true
    ) {
        model = CategoriesListScreenState(lce)
        if (shouldRefreshView)
            refreshView()
    }

    override suspend fun getFetchScreenData(allowCachedResult: Boolean): Lce<List<CategoryDataView>> {
        val lce = categoriesInteractor.getCategoriesList()
        return lce.toLceWithTransformedContent(categoryMapper::mapCategories)
    }

    override fun getUpdatedModelForLce(lce: Lce<List<CategoryDataView>>?): CategoriesListScreenState {
        updateScreenState(lce = lce)
        return model
    }

    fun onCategoriesListItemClicked(categoryDataView: CategoryDataView) =
        executeCommand(CategoriesCommands.OpenEventList(categoryDataView))

    fun onNavigationBackClicked() =
        executeCommand(CategoriesCommands.NavigateToPreviousScreen)
}