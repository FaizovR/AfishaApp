package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.EventShortInfoResponse
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventShortInfoMapper : EntityMapper<EventShortInfoResponse, EventShortInfo> {
    override fun mapFromEntity(entity: EventShortInfoResponse): EventShortInfo =
        EventShortInfo(
            id = entity.id ?: -1,
            title = entity.title ?: "",
            slug = entity.slug ?: "",
            description = entity.description ?: "",
            image = entity.image ?: ""
        )

    override fun mapToEntity(domainModel: EventShortInfo): EventShortInfoResponse =
        EventShortInfoResponse(
            id = domainModel.id,
            title = domainModel.title,
            slug = domainModel.slug,
            description = domainModel.description,
            image = domainModel.image
        )
}