package ru.faizovr.afisha.data.model

class EventShortApiItem(
    val id: Long,
    val title: String,
    val slug: String,
    val description: String,
    val dates: List<DateIntervalApiItem>,
    val images: List<ImageApiItem>
)