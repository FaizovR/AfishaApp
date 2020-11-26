package ru.faizovr.afisha.data

import ru.faizovr.afisha.data.remote.callback.CategoriesCallback
import ru.faizovr.afisha.data.remote.callback.EventListCallback

interface Repository {

    fun getCategoryFromApi(callback: CategoriesCallback)

    fun loadEventListFromApi(callback: EventListCallback, categoryName: String)
}