package ru.faizovr.afisha.presentation.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.faizovr.afisha.domain.interactors.EventsInteractor
import ru.faizovr.afisha.domain.model.EventPagedList
import ru.faizovr.afisha.domain.model.EventShort

class EventListDataSource(
    private val eventListInteractor: EventsInteractor,
    private val categoryTag: String,
) : PagingSource<String, EventShort>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, EventShort> {
        val currentLoadingPageKey = params.key ?: FIRST_PAGE
        return try {
            val result: EventPagedList =
                eventListInteractor.getEventList(currentLoadingPageKey, categoryTag)
            LoadResult.Page(
                data = result.events,
                prevKey = result.previousPage,
                nextKey = result.nextPage
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, EventShort>): String {
        return state.anchorPosition.toString()
    }

    companion object {
        private const val FIRST_PAGE = "1"
    }
}