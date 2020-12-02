package ru.faizovr.afisha.presentation.presenter

import android.util.Log
import ru.faizovr.afisha.data.Repository
import ru.faizovr.afisha.data.remote.callback.CategoriesCallback
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.contract.CategoryContract

class CategoryPresenter(
    private val view: CategoryContract.View,
    private val repository: Repository
) : CategoryContract.Presenter, CategoriesCallback {

    private val categoryList: MutableList<Category> = mutableListOf()

    override fun init() {
        repository.getCategoryFromApi(this)
        showProgressBar()
    }

    override fun onCategoryItemClickedForPosition(position: Int) {
        view.showNewFragment(categoryList[position])
        Log.d("TAG", "onCategoryItemClickedForPosition: category name = ${categoryList[position].name}")
    }

    override fun onCategoryDataLoaded(data: List<Category>?) {
        if (data != null) {
            showList(data)
            categoryList.clear()
            categoryList.addAll(data)
        }
    }

    override fun onError() {
        showErrorText()
    }

    private fun showErrorText() {
        view.setErrorTextVisibility(true)
        view.setCategoryListVisibility(false)
        view.setProgressBarVisibility(false)
    }

    private fun showProgressBar() {
        view.setProgressBarVisibility(true)
        view.setErrorTextVisibility(false)
        view.setCategoryListVisibility(false)
    }

    private fun showList(list: List<Category>) {
        view.setCategoryListVisibility(true)
        view.setErrorTextVisibility(false)
        view.setProgressBarVisibility(false)
        view.showList(list.map { it.name })
    }
}