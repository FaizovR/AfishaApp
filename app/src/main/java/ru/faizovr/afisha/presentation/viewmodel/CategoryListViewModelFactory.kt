package ru.faizovr.afisha.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faizovr.afisha.data.repository.Repository

@Suppress("UNCHECKED_CAST")
class CategoryListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryListViewModel(repository) as T
    }
}