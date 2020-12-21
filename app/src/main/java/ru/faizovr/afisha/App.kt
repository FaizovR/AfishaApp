package ru.faizovr.afisha

import android.app.Application
import ru.faizovr.afisha.data.RepositoryImpl
import ru.faizovr.afisha.data.remote.service.ApiService

class App : Application() {

    lateinit var repository: RepositoryImpl
        private set

    override fun onCreate() {
        super.onCreate()
        setupRepository()
    }

    private fun setupRepository() {
        repository = RepositoryImpl(ApiService.instance)
    }
}