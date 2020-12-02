package ru.faizovr.afisha.domain.model

class EventList(
    val count: Long,
    val nextPage: String?,
    val previousPage: String?,
    val events: List<EventShortInfo>
)
