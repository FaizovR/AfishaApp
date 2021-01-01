package ru.faizovr.afisha.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.faizovr.afisha.data.model.CategoryResponse
import ru.faizovr.afisha.data.model.EventListInfoResponse

interface ApiService {

    @GET("event-categories/?lang=ru")
    suspend fun getCategoriesList(): Response<List<CategoryResponse>>

    @GET("events/")
    suspend fun getEvents(
        @Query("fields") fields: String,
        @Query("categories") categoryName: String,
        @Query("page_size") pageSize: Int,
        @Query("page") page: String,
        @Query("order_by") order: String,
        @Query("actual_since") actual_since: Long,
        @Query("location") location: String,
        @Query("text_format") text_format: String = "text"
    ): Response<EventListInfoResponse>

    companion object {
        private const val API_VERSION = "v1.4"
        const val API_BASE_URL = "https://kudago.com/public-api/$API_VERSION/"
    }
}