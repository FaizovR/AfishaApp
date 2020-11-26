package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.EventShortInfoResponse
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventShortInfoMapper : EntityMapper<EventShortInfoResponse, EventShortInfo> {
    override fun mapFromEntity(entity: EventShortInfoResponse): EventShortInfo =
        EventShortInfo(
            id = entity.id ?: -1,
            tittle = entity.tittle ?: "",
            slug = entity.slug ?: ""
        )

    override fun mapToEntity(domainModel: EventShortInfo): EventShortInfoResponse =
        EventShortInfoResponse(
            id = domainModel.id,
            tittle = domainModel.tittle,
            slug = domainModel.slug
        )
}