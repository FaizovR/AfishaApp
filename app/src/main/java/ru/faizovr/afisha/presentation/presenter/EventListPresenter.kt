package ru.faizovr.afisha.presentation.presenter

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.faizovr.afisha.data.datasource.EventListDataSource
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.contract.EventListContract
import ru.faizovr.afisha.presentation.mapper.EventListDataViewMapper
import ru.faizovr.afisha.presentation.model.EventListDataView

class EventListPresenter(
    private val view: EventListContract.View,
    private val repository: Repository,
    private val categoryTag: String,
    private val eventListDataViewMapper: EventListDataViewMapper = EventListDataViewMapper()
) : EventListContract.Presenter {

    private lateinit var listData: Flow<PagingData<EventShortInfo>>

    override fun init() {
        listData = Pager(PagingConfig(pageSize = 20)) {
            EventListDataSource(repository, categoryTag)
        }.flow
        view.setupView()
        val events = listData.map { pagingData ->
            pagingData.map {
                eventListDataViewMapper.mapFromEntity(it)
            }
        }
        view.setupDataToList(events)
    }

    override fun onRetryButtonClicked() {
        view.onRetryClicked()
    }

    override fun onEventClicked(eventListDataView: EventListDataView) {
        view.showNewFragment(eventListDataView)
    }

    override fun onLoadStateChanged(loadState: CombinedLoadStates) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                view.setProgressBarVisibility(true)
                view.setErrorTextVisibility(false)
                view.setRetryButtonVisibility(false)
                view.setEventListVisibility(false)
            }
            is LoadState.Error -> {
                view.setProgressBarVisibility(false)
                view.setErrorTextVisibility(true)
                view.setRetryButtonVisibility(true)
                view.setEventListVisibility(false)
            }
            else -> {
                view.setProgressBarVisibility(false)
                view.setErrorTextVisibility(false)
                view.setRetryButtonVisibility(false)
                view.setEventListVisibility(true)
            }
        }
    }
}