package ru.faizovr.afisha.data.repository

import kotlinx.coroutines.flow.Flow
import ru.faizovr.afisha.core.data.repository.BaseRepository
import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.data.TimeProvider
import ru.faizovr.afisha.data.mapper.ApiMapper
import ru.faizovr.afisha.data.model.CategoryApiItem
import ru.faizovr.afisha.data.remote.KudaGoApi
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.Event
import ru.faizovr.afisha.domain.model.EventPagedList

class Repository(
    private val kudaGoApi: KudaGoApi,
    private val mapper: ApiMapper
) : BaseRepository() {

    suspend fun getCategoriesList(): Flow<Lce<List<Category>>> =
        safeApiCall {
            val categoriesList: List<CategoryApiItem> = kudaGoApi.getCategoriesList()
            mapResult(categoriesList, mapper::mapCategoriesList)
        }

    // TODO: 02.03.2021 вынести поля
    suspend fun getEventList(page: String, categoryTag: String): EventPagedList {
        val events = kudaGoApi.getEvents(
            FIELDS_TO_RETRIEVE,
            categoryTag,
            PAGE_SIZE,
            page,
            ORDER_TYPE,
            TimeProvider().currentTimeInSeconds(),
            LOCATION
        )
        return mapResult(events, mapper::map)
    }

    suspend fun getEventDetail(eventId: Long): Flow<Lce<Event>> =
        safeApiCall {
            val eventInfo = kudaGoApi.getEventInfo(eventId, FIELDS_TO_EXPAND)
            mapResult(eventInfo, mapper::map)
        }

    companion object {
        private const val FIELDS_TO_EXPAND = "place"
        private const val FIELDS_TO_RETRIEVE = "id,dates,title,place,price,description,images"
        private const val LOCATION = "msk"
        private const val PAGE_SIZE = 20
        private const val ORDER_TYPE = "publication_date"
    }
}