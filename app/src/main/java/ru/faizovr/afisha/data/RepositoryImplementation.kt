package ru.faizovr.afisha.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.faizovr.afisha.data.mapper.CategoryMapper
import ru.faizovr.afisha.data.model.CategoriesResponse
import ru.faizovr.afisha.data.remote.callback.CategoriesCallback
import ru.faizovr.afisha.data.remote.service.ApiService

class RepositoryImplementation(private val apiService: ApiService) : Repository {

    private val categoryMapper: CategoryMapper = CategoryMapper()

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
}