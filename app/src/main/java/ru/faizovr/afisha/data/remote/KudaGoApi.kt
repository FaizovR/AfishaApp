package ru.faizovr.afisha.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.faizovr.afisha.data.model.CategoryApiItem
import ru.faizovr.afisha.data.model.EventDetailApiItem
import ru.faizovr.afisha.data.model.EventListApiItem

interface KudaGoApi {
    // TODO: 02.03.2021 lang ru доавить ко всем параметрам и перенсти в аргументы
    @GET("event-categories/?lang=ru")
    suspend fun getCategoriesList(): List<CategoryApiItem>

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
    ): EventListApiItem

    @GET("events/{id}/")
    suspend fun getEventInfo(
        @Path("id") id: Long,
        @Query("expand") fieldsToExpand: String,
        @Query("text_format") text_format: String = "text",
    ): EventDetailApiItem
}