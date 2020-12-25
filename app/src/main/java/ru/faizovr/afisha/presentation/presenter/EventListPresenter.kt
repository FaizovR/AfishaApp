package ru.faizovr.afisha.presentation.presenter

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import ru.faizovr.afisha.data.datasource.EventListDataSource
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.adapter.EventListAdapter
import ru.faizovr.afisha.presentation.contract.EventListContract

class EventListPresenter(
    private val view: EventListContract.View,
    private val repository: Repository,
    private val category: Category,
) : EventListContract.Presenter {

    private var eventListAdapter: EventListAdapter? = null
    private lateinit var listData: Flow<PagingData<EventShortInfo>>

    override fun init() {
        this.eventListAdapter = EventListAdapter()
        listData = Pager(PagingConfig(pageSize = 20)) {
            EventListDataSource(repository, category)
        }.flow
        val eventListAdapter = eventListAdapter
        if (eventListAdapter != null) {
            view.setupView()
            view.setupList(eventListAdapter)
            view.setupDataToList(listData, eventListAdapter)
        }
    }

    override fun onRetryButtonClicked() {
        eventListAdapter?.retry()
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