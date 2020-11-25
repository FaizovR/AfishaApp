package ru.faizovr.afisha.data.remote.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder(url: String) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun buildApiService() : ApiService =
        retrofit.create(ApiService::class.java)
}