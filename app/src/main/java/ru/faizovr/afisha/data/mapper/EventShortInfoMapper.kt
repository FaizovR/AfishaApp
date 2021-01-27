package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.EventShortInfoResponse
import ru.faizovr.afisha.data.model.ImagesResponse
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventShortInfoMapper(
    private val datesMapper: DatesMapper = DatesMapper(),
) : EntityMapper<EventShortInfoResponse, EventShortInfo> {
    override fun mapFromEntity(entity: EventShortInfoResponse): EventShortInfo =
        EventShortInfo(
            id = entity.id ?: -1,
            title = entity.title ?: "",
            slug = entity.slug ?: "",
            description = entity.description ?: "",
            dates = datesMapper.mapFromEntity(entity.dates),
            images = mapImage(entity.images)
        )

    private fun mapImage(imagesResponse: List<ImagesResponse>?): List<String> =
        imagesResponse?.map(ImagesResponse::image) ?: emptyList()
}