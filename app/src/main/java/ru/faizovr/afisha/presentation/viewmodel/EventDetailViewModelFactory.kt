package ru.faizovr.afisha.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faizovr.afisha.data.repository.Repository

@Suppress("UNCHECKED_CAST")
class EventDetailViewModelFactory(
    private val repository: Repository,
    private val eventId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventDetailViewModel(repository, eventId) as T
    }
}