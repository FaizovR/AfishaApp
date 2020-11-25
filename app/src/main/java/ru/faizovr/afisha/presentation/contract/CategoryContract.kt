package ru.faizovr.afisha.presentation.contract

import ru.faizovr.afisha.domain.model.Category

interface CategoryContract {

    interface View {
        fun showNewFragment(category: Category)
        fun setErrorTextVisibility(isVisible: Boolean)
        fun setProgressBarVisibility(isVisible: Boolean)
        fun setCategoryListVisibility(isVisible: Boolean)
        fun showList(list: List<String>)
    }

    interface Presenter {
        fun init()
        fun onCategoryItemClickedForPosition(position: Int)
    }
}