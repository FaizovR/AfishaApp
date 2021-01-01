package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.DatesResponse
import ru.faizovr.afisha.data.model.EventShortInfoResponse
import ru.faizovr.afisha.data.model.ImagesResponse
import ru.faizovr.afisha.domain.model.Dates
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.domain.model.Images
import java.util.*

class EventShortInfoMapper : EntityMapper<EventShortInfoResponse, EventShortInfo> {
    override fun mapFromEntity(entity: EventShortInfoResponse): EventShortInfo =
        EventShortInfo(
            id = entity.id ?: -1,
            title = entity.title ?: "",
            slug = entity.slug ?: "",
            description = entity.description ?: "",
            dates = mapDate(entity.dates),
            images = mapImage(entity.images)
        )

    private fun mapImage(imagesResponse: List<ImagesResponse>?): List<Images> =
        imagesResponse?.map { Images(it.image) } ?: emptyList()

    private fun mapDate(datesResponse: List<DatesResponse>?): List<Dates> {
        return datesResponse?.map { Dates(Date(it.start.times(1000)), Date(it.end.times(1000))) }
            ?: emptyList()
    }
}