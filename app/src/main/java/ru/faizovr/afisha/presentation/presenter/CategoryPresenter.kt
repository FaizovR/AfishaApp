package ru.faizovr.afisha.presentation.presenter

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.contract.CategoryContract

class CategoryPresenter(
    private val view: CategoryContract.View,
    private val repository: Repository
) : CategoryContract.Presenter {

    private val categoryList: MutableList<Category> = mutableListOf()

    init {
        showProgressBar()
        loadCategoryList()
    }

    private fun loadCategoryList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getCategoriesList()
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    showList(result.value)
                    categoryList.clear()
                    categoryList.addAll(result.value)
                } else {
                    Log.e("TAG", "loadCategoryList: ${(result as Result.Error).exception}")
                    showErrorText()
                }
            }
        }
    }

    override fun onRetryClicked() {
        showProgressBar()
        loadCategoryList()
    }

    override fun onCategoryItemClickedForPosition(position: Int) {
        view.showNewFragment(categoryList[position])
    }

    private fun showErrorText() {
        view.setErrorTextVisibility(true)
        view.setRetryButtonVisibility(true)
        view.setCategoryListVisibility(false)
        view.setProgressBarVisibility(false)
    }

    private fun showProgressBar() {
        view.setProgressBarVisibility(true)
        view.setRetryButtonVisibility(false)
        view.setErrorTextVisibility(false)
        view.setCategoryListVisibility(false)
    }

    private fun showList(list: List<Category>) {
        view.setCategoryListVisibility(true)
        view.setRetryButtonVisibility(false)
        view.setErrorTextVisibility(false)
        view.setProgressBarVisibility(false)
        view.showList(list.map(Category::name))
    }
}