package ru.faizovr.afisha.data.model

data class EventDetailInfoResponse(
    val age_restriction: String,
    val body_text: String,
    val categories: List<String>,
    val comments_count: Int,
    val dates: List<DatesResponse>,
    val description: String,
    val disable_comments: Boolean,
    val favorites_count: Int,
    val id: Int,
    val images: List<ImagesResponse>,
    val is_free: Boolean,
    val price: String,
    val publication_date: Int,
    val short_title: String,
    val site_url: String,
    val slug: String,
    val tagline: String,
    val tags: List<String>,
    val title: String
)