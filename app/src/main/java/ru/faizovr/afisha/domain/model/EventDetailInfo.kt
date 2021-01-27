package ru.faizovr.afisha.domain.model

data class EventDetailInfo(
    val id: Int,
    val title: String,
    val ageRestriction: String,
    val fullDescription: String,
    val categories: List<String>,
    val commentsCount: Int,
    val dates: List<Dates>,
    val shortDescription: String,
    val isCommentsDisabled: Boolean,
    val favoritesCount: Int,
    val images: List<String>,
    val isFree: Boolean,
    val price: String,
    val publicationDate: Int,
    val shortTitle: String,
    val siteUrl: String,
    val slug: String,
    val tagLine: String,
    val tags: List<String>,
)