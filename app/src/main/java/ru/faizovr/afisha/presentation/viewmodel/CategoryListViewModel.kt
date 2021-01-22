package ru.faizovr.afisha.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.base.BaseViewModel
import ru.faizovr.afisha.presentation.mapper.CategoryDataViewMapper
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryListViewModel(
    private val repository: Repository,
    private val categoryMapper: CategoryDataViewMapper = CategoryDataViewMapper()
) : BaseViewModel() {

    private val categoriesList: MutableList<Category> = mutableListOf()

    private val _categoriesDataViewList = MutableLiveData<List<CategoryDataView>>()
    val categoriesDataViewList: LiveData<List<CategoryDataView>> = _categoriesDataViewList

    private val _categoriesListVisibility = MutableLiveData<Boolean>()
    val categoriesListVisibility: LiveData<Boolean> = _categoriesListVisibility

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        setLoadingState()
        viewModelScope.launch {
            val result = repository.getCategoriesList()
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    refreshCategoriesList(result.value)
                    refreshCategoriesDataViewList()
                    setDefaultState()
                } else {
                    Log.e("TAG", "loadCategoryList: ${(result as Result.Error).exception}")
                    setErrorState()
                }
            }
        }
    }

    fun onRetryClicked() {
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

    override fun setDefaultState() {
        super.setDefaultState()
        _categoriesListVisibility.value = true
    }

    override fun setLoadingState() {
        super.setLoadingState()
        _categoriesListVisibility.value = false
    }

    override fun setErrorState() {
        super.setErrorState()
        _categoriesListVisibility.value = false
    }
}