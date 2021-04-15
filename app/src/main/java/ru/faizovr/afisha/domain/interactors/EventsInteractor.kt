package ru.faizovr.afisha.domain.interactors

import ru.faizovr.afisha.core.domain.models.Lce
import ru.faizovr.afisha.data.repository.Repository
import ru.faizovr.afisha.domain.model.Event
import ru.faizovr.afisha.domain.model.EventPagedList

class EventsInteractor(
    private val repository: Repository
) {
    suspend fun getEventById(id: Long): Lce<Event> =
        repository.getEventDetail(id)

    suspend fun getEventList(page: String, categoryTag: String): EventPagedList =
        repository.getEventList(page, categoryTag)
}