package ru.faizovr.afisha.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ru.faizovr.afisha.data.EventListDataSourceFactory
import ru.faizovr.afisha.data.Repository
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.contract.EventListContract

class EventListPresenter(
    private val view: EventListContract.View,
    private val repository: Repository,
    private val category: Category
) : EventListContract.Presenter {

    private lateinit var eventListDataSource: EventListDataSourceFactory
    private lateinit var events: LiveData<PagedList<EventShortInfo>>

    override fun init() {

        eventListDataSource = EventListDataSourceFactory(repository, category)
        events = LivePagedListBuilder(eventListDataSource, pagedListConfig()).build()
        view.setupLiveData(events)
    }

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(true)
        .setPageSize(20)
        .build()


}