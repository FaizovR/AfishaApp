package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.EventDetailInfoResponse
import ru.faizovr.afisha.data.model.ImagesResponse
import ru.faizovr.afisha.domain.model.EventDetailInfo

class EventDetailMapper(
    private val datesMapper: DatesMapper = DatesMapper(),
) : EntityMapper<EventDetailInfoResponse, EventDetailInfo> {

    override fun mapFromEntity(entity: EventDetailInfoResponse): EventDetailInfo =
        EventDetailInfo(
            id = entity.id,
            title = entity.title ?: "",
            fullDescription = entity.body_text ?: "",
            dates = datesMapper.mapFromEntity(entity.dates),
            images = mapImage(entity.images),
        )

    private fun mapImage(imagesResponse: List<ImagesResponse>?): List<String> =
        imagesResponse?.map(ImagesResponse::image) ?: emptyList()
}