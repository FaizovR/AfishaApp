package ru.faizovr.afisha.domain.model

import ru.faizovr.afisha.core.domain.models.DateInterval

class EventShort(
    val id: Long,
    val title: String,
    val description: String,
    val dates: List<DateInterval>,
    val images: List<String>,
)