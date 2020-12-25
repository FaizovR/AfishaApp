package ru.faizovr.afisha.data.model

class EventShortInfoResponse(
    val id: Long?,
    val title: String?,
    val slug: String?,
    val description: String?,
    val images: List<ImagesResponse>?
)