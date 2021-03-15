package ru.faizovr.afisha.data.model

class EventListApiItem(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<EventShortApiItem>
)