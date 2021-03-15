package ru.faizovr.afisha.domain.model

import ru.faizovr.afisha.core.domain.models.DateInterval

class Event(
    val id: Int,
    val title: String,
    val fullDescription: String,
    val dates: List<DateInterval>,
    val images: List<String>,
)