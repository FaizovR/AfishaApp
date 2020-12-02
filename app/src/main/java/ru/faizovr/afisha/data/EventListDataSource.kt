package ru.faizovr.afisha.data

import androidx.paging.PageKeyedDataSource
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventList
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventListDataSource(
    private val repository: Repository,
    private val category: Category
) :
    PageKeyedDataSource<String, EventShortInfo>() {

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, EventShortInfo>
    ) {
        val events: EventList? = repository.getEventList("1", category)
        val next = events?.nextPage
        callback.onResult(events?.events!!, null, next)
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, EventShortInfo>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, EventShortInfo>
    ) {
        val events: EventList? = repository.getEventList(params.key, category)
        val next = events?.nextPage
        callback.onResult(events?.events!!, next)
    }
}