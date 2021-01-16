package ru.faizovr.afisha.data.repository

import ru.faizovr.afisha.data.wrapper.Result
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.EventDetailInfo
import ru.faizovr.afisha.domain.model.EventListInfo

interface Repository {

    suspend fun getCategoriesList(): Result<List<Category>>

    suspend fun getEventList(page: String, categoryTag: String): Result<EventListInfo>

    suspend fun getEventDetail(eventId: Long): Result<EventDetailInfo>
}