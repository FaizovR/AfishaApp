package ru.faizovr.afisha.data

import android.util.Log
import androidx.paging.PagingSource
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventList
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventListDataSource(
    private val repository: Repository,
    private val category: Category,
) :
    PagingSource<String, EventShortInfo>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, EventShortInfo> =
        try {
            val currentLoadingPageKey = params.key ?: "1"
            val response: EventList? = repository.getEventList(currentLoadingPageKey, category)
            val data: List<EventShortInfo> = response?.events ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = response?.previousPage,
                nextKey = response?.nextPage
            )
        } catch (e: Exception) {
            Log.d("TAG", "load:  ERROR")
            LoadResult.Error(e)
        }
}