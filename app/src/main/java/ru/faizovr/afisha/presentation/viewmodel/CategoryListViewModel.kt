package ru.faizovr.afisha.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.ScreenState
import ru.faizovr.afisha.presentation.mapper.CategoryDataViewMapper
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryListViewModel(
    private val repository: Repository,
    private val categoryMapper: CategoryDataViewMapper = CategoryDataViewMapper()
) : ViewModel() {

    private val categoriesList: MutableList<Category> = mutableListOf()

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private val _categoriesDataViewList = MutableLiveData<List<CategoryDataView>>()
    val categoriesDataViewList: LiveData<List<CategoryDataView>> = _categoriesDataViewList

    init {
        loadingState()
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            val result = repository.getCategoriesList()
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    refreshCategoriesList(result.value)
                    refreshCategoriesDataViewList()
                    defaultState()
                } else {
                    Log.e("TAG", "loadCategoryList: ${(result as Result.Error).exception}")
                    errorState()
                }
            }
        }
    }

    fun onRetryClicked() {
        loadingState()
        fetchInfo()
    }

    private fun refreshCategoriesList(list: List<Category>) {
        categoriesList.clear()
        categoriesList.addAll(list)
    }

    private fun refreshCategoriesDataViewList() {
        val map = categoriesList.map(categoryMapper::mapFromEntity)
        _categoriesDataViewList.value = map
    }

    private fun defaultState() {
        _screenState.value = ScreenState.Default
    }

    private fun loadingState() {
        _screenState.value = ScreenState.Loading
    }

    private fun errorState() {
        _screenState.value = ScreenState.Error
    }
}