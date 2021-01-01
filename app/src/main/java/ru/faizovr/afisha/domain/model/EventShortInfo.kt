package ru.faizovr.afisha.domain.model

class EventShortInfo(
    val id: Long,
    val title: String,
    val slug: String,
    val description: String,
    val dates: List<Dates>,
    val images: List<Images>,
)