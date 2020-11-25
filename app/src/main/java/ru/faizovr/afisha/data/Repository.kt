package ru.faizovr.afisha.data

import ru.faizovr.afisha.data.remote.callback.CategoriesCallback

interface Repository {
    fun getCategoryFromApi(callback: CategoriesCallback)
}