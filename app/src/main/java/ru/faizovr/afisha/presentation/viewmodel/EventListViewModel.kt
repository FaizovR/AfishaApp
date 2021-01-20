package ru.faizovr.afisha.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.faizovr.afisha.data.datasource.EventListDataSource
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.presentation.mapper.EventListDataViewMapper
import ru.faizovr.afisha.presentation.model.EventListDataView

class EventListViewModel(
    private val repository: Repository,
    bundle: Bundle,
    private val eventListDataViewMapper: EventListDataViewMapper = EventListDataViewMapper()
) : ViewModel() {

    var listData: Flow<PagingData<EventListDataView>>

    private val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        EventListDataSource(repository, categoryTag)
    }.flow
        .map { pagingData ->
            pagingData.map {
                eventListDataViewMapper.mapFromEntity(it)
            }
        }
        .cachedIn(viewModelScope)

    private val categoryTag: String = bundle.getString("Category_Tag", "")

    private val _eventListVisibility = MutableLiveData<Boolean>()
    val eventListVisibility: LiveData<Boolean> = _eventListVisibility

    private val _buttonRetryVisibility = MutableLiveData<Boolean>()
    val buttonRetryVisibility: LiveData<Boolean> = _buttonRetryVisibility

    private val _errorTextVisibility = MutableLiveData<Boolean>()
    val errorTextVisibility: LiveData<Boolean> = _errorTextVisibility

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> = _progressBarVisibility

    init {
        loadingState()
        listData = flow
    }

    fun onLoadStateChanged(loadState: CombinedLoadStates) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                loadingState()
            }
            is LoadState.Error -> {
                errorState()
            }
            else -> {
                defaultState()
            }
        }
    }

    private fun defaultState() {
        _eventListVisibility.value = true
        _progressBarVisibility.value = false
        _buttonRetryVisibility.value = false
        _errorTextVisibility.value = false
    }

    private fun loadingState() {
        _eventListVisibility.value = false
        _progressBarVisibility.value = true
        _buttonRetryVisibility.value = false
        _errorTextVisibility.value = false
    }

    private fun errorState() {
        _eventListVisibility.value = false
        _progressBarVisibility.value = false
        _buttonRetryVisibility.value = true
        _errorTextVisibility.value = true
    }
}