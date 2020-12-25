package ru.faizovr.afisha.data.model

class EventListInfoResponse(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<EventShortInfoResponse>?
)