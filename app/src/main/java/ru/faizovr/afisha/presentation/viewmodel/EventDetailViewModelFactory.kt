package ru.faizovr.afisha.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faizovr.afisha.data.repository.Repository

@Suppress("UNCHECKED_CAST")
class EventDetailViewModelFactory(
    private val repository: Repository,
    private val bundle: Bundle
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventDetailViewModel(repository, bundle) as T
    }
}