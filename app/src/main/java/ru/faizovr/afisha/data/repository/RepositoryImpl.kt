package ru.faizovr.afisha.data.repository

import ru.faizovr.afisha.data.TimeProvider
import ru.faizovr.afisha.data.mapper.CategoryMapper
import ru.faizovr.afisha.data.mapper.EventDetailMapper
import ru.faizovr.afisha.data.mapper.EventListMapper
import ru.faizovr.afisha.data.model.CategoryResponse
import ru.faizovr.afisha.data.model.EventDetailInfoResponse
import ru.faizovr.afisha.data.model.EventListInfoResponse
import ru.faizovr.afisha.data.remote.service.ApiService
import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventDetailInfo
import ru.faizovr.afisha.domain.model.EventListInfo

class RepositoryImpl(
    private val apiService: ApiService,
    private val categoryMapper: CategoryMapper = CategoryMapper(),
    private val eventListMapper: EventListMapper = EventListMapper(),
    private val eventDetailMapper: EventDetailMapper = EventDetailMapper(),
) : Repository, BaseRepository() {

    override suspend fun getCategoriesList(): Result<List<Category>> {
        val result: Result<List<CategoryResponse>> =
            safeApiCall({ apiService.getCategoriesList() }, "ERROR")
        return if (result is Result.Success) {
            Result.Success(mapResult(result.value, categoryMapper::mapFromEntity))
        } else
            Result.Error((result as Result.Error).exception)
    }


    override suspend fun getEventList(page: String, categoryTag: String): Result<EventListInfo> {
        val result: Result<EventListInfoResponse> =
            safeApiCall(
                {
                    apiService.getEvents(
                        FIELDS_TO_RETRIEVE,
                        categoryTag,
                        PAGE_SIZE,
                        page,
                        ORDER_TYPE,
                        TimeProvider().currentTimeInSeconds(),
                        LOCATION
                    )
                }, "Error"
            )
        return if (result is Result.Success) {
            Result.Success(mapResult(result.value, eventListMapper::mapFromEntity))
        } else {
            Result.Error((result as Result.Error).exception)
        }
    }

    override suspend fun getEventDetail(eventId: Long): Result<EventDetailInfo> {
        val result: Result<EventDetailInfoResponse> =
            safeApiCall(
                { apiService.getEventInfo(eventId, FIELDS_TO_EXPAND) }, "Error"
            )
        return if (result is Result.Success) {
            Result.Success(mapResult(result.value, eventDetailMapper::mapFromEntity))

        } else {
            Result.Error((result as Result.Error).exception)
        }
    }

    companion object {
        private const val FIELDS_TO_EXPAND = "place"
        private const val FIELDS_TO_RETRIEVE = "id,dates,title,place,price,description,images"
        private const val LOCATION = "msk"
        private const val PAGE_SIZE = 20
        private const val ORDER_TYPE = "publication_date"
    }
}