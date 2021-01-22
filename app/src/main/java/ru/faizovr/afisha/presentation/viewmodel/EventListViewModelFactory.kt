package ru.faizovr.afisha.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faizovr.afisha.data.repository.Repository

@Suppress("UNCHECKED_CAST")
class EventListViewModelFactory(
    private val repository: Repository,
    private val categoryTag: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventListViewModel(repository, categoryTag) as T
    }
}