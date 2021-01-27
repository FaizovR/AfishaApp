package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.EventListInfoResponse
import ru.faizovr.afisha.domain.model.EventListInfo

class EventListMapper : EntityMapper<EventListInfoResponse, EventListInfo> {

    private val eventShortInfoMapper: EventShortInfoMapper = EventShortInfoMapper()

    override fun mapFromEntity(entity: EventListInfoResponse): EventListInfo {
        val next = entity.next?.substringAfterLast("page=")?.substringBefore("&")
        val previous = entity.previous?.substringAfterLast("page=")?.substringBefore("&")
        val events = entity.results?.map(eventShortInfoMapper::mapFromEntity) ?: listOf()
        return EventListInfo(
            count = entity.count,
            nextPage = next,
            previousPage = previous,
            events = events
        )
    }
}