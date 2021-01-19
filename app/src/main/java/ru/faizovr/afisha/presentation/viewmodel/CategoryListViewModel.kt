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
import ru.faizovr.afisha.presentation.mapper.CategoryDataViewMapper
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryListViewModel(
    private val repository: Repository,
    private val categoryMapper: CategoryDataViewMapper = CategoryDataViewMapper()
) : ViewModel() {

    private val categoriesList: MutableList<Category> = mutableListOf()

    private val _categoriesDataViewList = MutableLiveData<List<CategoryDataView>>()
    val categoriesDataViewList: LiveData<List<CategoryDataView>> = _categoriesDataViewList

    private val _categoriesListVisibility = MutableLiveData<Boolean>()
    val categoriesListVisibility: LiveData<Boolean> = _categoriesListVisibility

    private val _buttonRetryVisibility = MutableLiveData<Boolean>()
    val buttonRetryVisibility: LiveData<Boolean> = _buttonRetryVisibility

    private val _errorTextVisibility = MutableLiveData<Boolean>()
    val errorTextVisibility: LiveData<Boolean> = _errorTextVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> = _progressBarVisibility


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
        _categoriesListVisibility.value = true
        _progressBarVisibility.value = false
        _buttonRetryVisibility.value = false
        _errorTextVisibility.value = false
    }

    private fun loadingState() {
        _categoriesListVisibility.value = false
        _progressBarVisibility.value = true
        _buttonRetryVisibility.value = false
        _errorTextVisibility.value = false
    }

    private fun errorState() {
        _categoriesListVisibility.value = false
        _progressBarVisibility.value = false
        _buttonRetryVisibility.value = true
        _errorTextVisibility.value = true
    }
}