package ru.faizovr.afisha.data.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.faizovr.afisha.data.model.CategoriesResponse
import ru.faizovr.afisha.data.model.EventListResponse

interface ApiService {

    @GET("event-categories/?lang=ru")
    fun getListCategories(): Call<List<CategoriesResponse>>

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
        private const val API_BASE_URL = "https://kudago.com/public-api/$API_VERSION/"

        val instance: ApiService by lazy {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            httpClient.addInterceptor(loggingInterceptor)

            val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

            retrofit.create<ApiService>(ApiService::class.java)
        }
    }

}