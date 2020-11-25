package ru.faizovr.afisha.data.remote.service

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.faizovr.afisha.data.model.CategoriesResponse

interface ApiService {

    @GET("event-categories/?lang=ru")
    fun getListCategories() : Call<List<CategoriesResponse>>

    companion object {
        private const val API_VERSION = "v1.4"
        private const val API_BASE_URL = "https://kudago.com/public-api/$API_VERSION/"

        val instance: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create<ApiService>(ApiService::class.java)
        }
    }

}