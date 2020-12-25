package ru.faizovr.afisha

import android.app.Application
import ru.faizovr.afisha.data.remote.service.ApiService
import ru.faizovr.afisha.data.remote.service.ApiServiceBuilder
import ru.faizovr.afisha.data.repository.RepositoryImpl

class App : Application() {

    lateinit var repository: RepositoryImpl
        private set

    override fun onCreate() {
        super.onCreate()
        setupRepository()
    }

    private fun setupRepository() {
        val apiService = ApiServiceBuilder(ApiService.API_BASE_URL).buildService()
        repository = RepositoryImpl(apiService)
    }
}