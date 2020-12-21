package ru.faizovr.afisha.data.remote.service

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.faizovr.afisha.data.model.CategoryResponse
import ru.faizovr.afisha.data.model.EventListResponse

interface ApiService {

    @GET("event-categories/?lang=ru")
    fun getCategoriesList(): Call<List<CategoryResponse>>

    @GET("events/")
    suspend fun getEvents(
        @Query("fields") fields: String,
        @Query("categories") categoryName: String,
        @Query("page_size") pageSize: Int,
        @Query("page") page: String,
        @Query("order_by") order: String,
        @Query("actual_since") actual_since: String,
        @Query("text_format") text_format: String = "text"
    ): Response<EventListResponse>

    companion object {
        private const val API_VERSION = "v1.4"
        const val API_BASE_URL = "https://kudago.com/public-api/$API_VERSION/"
    }
}