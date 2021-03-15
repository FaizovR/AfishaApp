package ru.faizovr.afisha.domain.model

class EventPagedList(
    val count: Long,
    val nextPage: String?,
    val previousPage: String?,
    val events: List<EventShort>
)
