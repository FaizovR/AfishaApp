package ru.faizovr.afisha.data.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.faizovr.afisha.data.model.CategoriesResponse
import ru.faizovr.afisha.data.model.EventListResponse

interface ApiService {

    @GET("event-categories/?lang=ru")
    fun getListCategories() : Call<List<CategoriesResponse>>

    @GET("events/?")
    fun getEventList(@Query("category") categoryName: String) : Call<EventListResponse>

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