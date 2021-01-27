package ru.faizovr.afisha.domain.model

class EventDetailInfo(
    val id: Int,
    val title: String,
    val fullDescription: String,
    val dates: List<Dates>,
    val images: List<String>,
)