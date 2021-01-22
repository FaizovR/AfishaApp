package ru.faizovr.afisha.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.faizovr.afisha.data.datasource.EventListDataSource
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.presentation.base.BaseViewModel
import ru.faizovr.afisha.presentation.mapper.EventListDataViewMapper
import ru.faizovr.afisha.presentation.model.EventListDataView

class EventListViewModel(
    private val repository: Repository,
    private val categoryTag: String,
    private val eventListDataViewMapper: EventListDataViewMapper = EventListDataViewMapper()
) : BaseViewModel() {

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

    private val _eventListVisibility = MutableLiveData<Boolean>()
    val eventListVisibility: LiveData<Boolean> = _eventListVisibility

    init {
        setLoadingState()
        listData = flow
    }

    fun onLoadStateChanged(loadState: CombinedLoadStates) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                setLoadingState()
            }
            is LoadState.Error -> {
                setErrorState()
            }
            else -> {
                setDefaultState()
            }
        }
    }

    override fun setDefaultState() {
        super.setDefaultState()
        _eventListVisibility.value = true
    }

    override fun setLoadingState() {
        super.setLoadingState()
        _eventListVisibility.value = false
    }

    override fun setErrorState() {
        super.setErrorState()
        _eventListVisibility.value = false
    }
}