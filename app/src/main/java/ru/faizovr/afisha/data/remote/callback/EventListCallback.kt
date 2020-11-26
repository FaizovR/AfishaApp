package ru.faizovr.afisha.data.remote.callback

import ru.faizovr.afisha.domain.model.EventList


interface EventListCallback {
    fun onEventListLoaded(data: EventList)
    fun onError()
}