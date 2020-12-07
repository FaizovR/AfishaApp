package ru.faizovr.afisha.domain.model

class EventShortInfo(
    val id: Long,
    val title: String,
    val slug: String,
    val description: String,
    val image: String,
) : Comparable<EventShortInfo> {
    override fun compareTo(other: EventShortInfo): Int {
        if (title != other.title) return -1
        if (slug != other.slug) return -1
        if (description != other.description) return -1
        if (image != other.image) return -1
        return 0
    }
}