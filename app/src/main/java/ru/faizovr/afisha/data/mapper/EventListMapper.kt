package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.EventListResponse
import ru.faizovr.afisha.domain.model.EventList

class EventListMapper : EntityMapper<EventListResponse, EventList> {

    private val eventShortInfoMapper: EventShortInfoMapper = EventShortInfoMapper()

    override fun mapFromEntity(entity: EventListResponse): EventList {
        val next = entity.next?.substringAfterLast("page=")?.substringBefore("&")
        val previous = entity.previous?.substringAfterLast("page=")?.substringBefore("&")
        return EventList(
            count = entity.count,
            nextPage = next,
            previousPage = previous,
            events = entity.results?.map { eventShortInfoMapper.mapFromEntity(it) } ?: listOf()
        )
    }

    override fun mapToEntity(domainModel: EventList): EventListResponse =
        EventListResponse(
            count = domainModel.count,
            next = "1",
            previous = "2",
            results = domainModel.events.map { eventShortInfoMapper.mapToEntity(it) }
        )
}