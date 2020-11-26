package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.EventListResponse
import ru.faizovr.afisha.domain.model.EventList

class EventListMapper : EntityMapper<EventListResponse, EventList> {

    private val eventShortInfoMapper: EventShortInfoMapper = EventShortInfoMapper()

    override fun mapFromEntity(entity: EventListResponse): EventList =
        EventList(
            count = entity.count,
            nextPage = 2,
            previousPage = 0,
            events = entity.results?.map { eventShortInfoMapper.mapFromEntity(it) } ?: listOf()
        )

    override fun mapToEntity(domainModel: EventList): EventListResponse =
        EventListResponse(
            count = domainModel.count,
            next = "1",
            previous = "2",
            results = domainModel.events.map { eventShortInfoMapper.mapToEntity(it) }
        )
}