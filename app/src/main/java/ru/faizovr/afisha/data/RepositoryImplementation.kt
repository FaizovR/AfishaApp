package ru.faizovr.afisha.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.faizovr.afisha.data.mapper.CategoryMapper
import ru.faizovr.afisha.data.mapper.EventListMapper
import ru.faizovr.afisha.data.model.CategoriesResponse
import ru.faizovr.afisha.data.model.EventListResponse
import ru.faizovr.afisha.data.remote.callback.CategoriesCallback
import ru.faizovr.afisha.data.remote.callback.EventListCallback
import ru.faizovr.afisha.data.remote.service.ApiService

class RepositoryImplementation(private val apiService: ApiService) : Repository {

    private val categoryMapper: CategoryMapper = CategoryMapper()
    private val eventListMapper: EventListMapper = EventListMapper()

    override fun getCategoryFromApi(callback: CategoriesCallback) {
        apiService.getListCategories().enqueue(object : Callback<List<CategoriesResponse>> {
            override fun onFailure(call: Call<List<CategoriesResponse>>, t: Throwable) {
                callback.onError()
            }

            override fun onResponse(
                call: Call<List<CategoriesResponse>>,
                response: Response<List<CategoriesResponse>>
            ) {
                callback.onCategoryDataLoaded(response.body()?.map { categoryMapper.mapFromEntity(it) })
            }
        })
    }

    override fun loadEventListFromApi(
        callback: EventListCallback,
        categoryName: String
    ) {
        apiService.getEventList(categoryName).enqueue(object : Callback<EventListResponse> {
            override fun onResponse(call: Call<EventListResponse>, response: Response<EventListResponse>) {
                val entity = response.body()
                if (entity != null) {
                    callback.onEventListLoaded(eventListMapper.mapFromEntity(entity))
                }
            }

            override fun onFailure(call: Call<EventListResponse>, t: Throwable) {
                callback.onError()
            }

        })
    }
}