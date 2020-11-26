package ru.faizovr.afisha.data.model

class EventListResponse(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<EventShortInfoResponse>?
)