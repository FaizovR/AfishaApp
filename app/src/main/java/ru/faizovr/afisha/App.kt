package ru.faizovr.afisha

import android.app.Application
import ru.faizovr.afisha.data.RepositoryImplementation
import ru.faizovr.afisha.data.remote.service.ApiService

class App : Application() {

    lateinit var repository: RepositoryImplementation
        private set

    override fun onCreate() {
        super.onCreate()
        setupRepository()
    }

    private fun setupRepository() {
        repository = RepositoryImplementation(ApiService.instance)
    }
}