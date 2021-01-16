package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.model.DatesResponse
import ru.faizovr.afisha.data.model.EventDetailInfoResponse
import ru.faizovr.afisha.data.model.ImagesResponse
import ru.faizovr.afisha.domain.model.Dates
import ru.faizovr.afisha.domain.model.EventDetailInfo
import ru.faizovr.afisha.domain.model.Images
import java.util.*

class EventDetailMapper : EntityMapper<EventDetailInfoResponse, EventDetailInfo> {
    override fun mapFromEntity(entity: EventDetailInfoResponse): EventDetailInfo {
        return EventDetailInfo(
            id = entity.id,
            title = entity.title,
            ageRestriction = entity.age_restriction,
            fullDescription = entity.body_text,
            categories = entity.categories,
            commentsCount = entity.comments_count,
            dates = mapDate(entity.dates),
            shortDescription = entity.description,
            isCommentsDisabled = entity.disable_comments,
            favoritesCount = entity.favorites_count,
            images = mapImage(entity.images),
            isFree = entity.is_free,
            price = entity.price,
            publicationDate = entity.publication_date,
            shortTitle = entity.short_title,
            siteUrl = entity.site_url,
            slug = entity.slug,
            tagLine = entity.tagline,
            tags = entity.tags
        )
    }

    private fun mapImage(imagesResponse: List<ImagesResponse>?): List<Images> =
        imagesResponse?.map { Images(it.image) } ?: emptyList()


    private fun mapDate(datesResponse: List<DatesResponse>?): List<Dates> {
        return datesResponse?.map { Dates(Date(it.start.times(1000)), Date(it.end.times(1000))) }
            ?: emptyList()
    }
}