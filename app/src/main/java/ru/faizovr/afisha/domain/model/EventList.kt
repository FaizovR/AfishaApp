package ru.faizovr.afisha.domain.model

class EventList(
    val count: Long,
    val nextPage: Int,
    val previousPage: Int,
    val events: List<EventShortInfo>
)
