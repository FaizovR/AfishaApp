package ru.faizovr.afisha.data.repository

import ru.faizovr.afisha.data.mapper.CategoryMapper
import ru.faizovr.afisha.data.mapper.EventListMapper
import ru.faizovr.afisha.data.model.CategoryResponse
import ru.faizovr.afisha.data.model.EventListResponse
import ru.faizovr.afisha.data.remote.service.ApiService
import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventList

class RepositoryImpl(
    private val apiService: ApiService,
    private val categoryMapper: CategoryMapper = CategoryMapper(),
    private val eventListMapper: EventListMapper = EventListMapper()
) : Repository, BaseRepository() {

    override suspend fun getCategoriesList(): Result<List<Category>> {
        val result: Result<List<CategoryResponse>> =
            safeApiCall({ apiService.getCategoriesList() }, "ERROR")
        return if (result is Result.Success) {
            Result.Success(result.value.map { categoryMapper.mapFromEntity(it) })
        } else
            Result.Error((result as Result.Error).exception)
    }


    override suspend fun getEventList(page: String, category: Category): EventList? {
        val eventPage = apiService.getEvents(
            LIST_FIELDS_TO_RETRIEVE,
            category.tag,
            PAGE_SIZE,
            page,
            ORDER_PUBLICATION_DATE,
            ACTUAL_SINCE

        )
        val body: EventListResponse? = eventPage.body()
        return if (body != null) {
            eventListMapper.mapFromEntity(body)
        } else {
            null
        }
    }

    companion object {
        private const val LIST_FIELDS_TO_RETRIEVE = "id,dates,title,place,price,description,images"
        private const val PAGE_SIZE = 20
        private const val ORDER_PUBLICATION_DATE = "publication_date"
        private const val ACTUAL_SINCE = "1606901406"
    }
}