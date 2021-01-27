package ru.faizovr.afisha.presentation.datasource

import android.util.Log
import androidx.paging.PagingSource
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.EventListInfo
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventListDataSource(
    private val repository: Repository,
    private val categoryTag: String,
) :
    PagingSource<String, EventShortInfo>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, EventShortInfo> {
        val currentLoadingPageKey = params.key ?: FIRST_PAGE
        val result: Result<EventListInfo>
        result = repository.getEventList(currentLoadingPageKey, categoryTag)
        return when (result) {
            is Result.Success -> {
                val eventList: EventListInfo = result.value
                LoadResult.Page(
                    data = eventList.events,
                    prevKey = eventList.previousPage,
                    nextKey = eventList.nextPage
                )
            }
            else -> {
                Log.e(TAG,
                    "loadCategoryList: ${(result as Result.Error).exception} ${result.exception.stackTrace.map { '\n' + it.toString() }}")
                LoadResult.Error(result.exception)
            }
        }
    }

    companion object {
        private const val TAG = "EventListDataSource"
        private const val FIRST_PAGE = "1"
    }
}