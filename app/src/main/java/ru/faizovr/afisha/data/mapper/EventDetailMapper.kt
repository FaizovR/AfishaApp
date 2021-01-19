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
            title = entity.title ?: "",
            ageRestriction = entity.age_restriction ?: "",
            fullDescription = entity.body_text ?: "",
            categories = entity.categories ?: emptyList(),
            commentsCount = entity.comments_count ?: 0,
            dates = mapDate(entity.dates),
            shortDescription = entity.description ?: "",
            isCommentsDisabled = entity.disable_comments ?: true,
            favoritesCount = entity.favorites_count ?: 0,
            images = mapImage(entity.images),
            isFree = entity.is_free ?: true,
            price = entity.price ?: "",
            publicationDate = entity.publication_date ?: 0,
            shortTitle = entity.short_title ?: "",
            siteUrl = entity.site_url ?: "",
            slug = entity.slug ?: "",
            tagLine = entity.tagLine ?: "",
            tags = entity.tags ?: emptyList()
        )
    }

    private fun mapImage(imagesResponse: List<ImagesResponse>?): List<Images> =
        imagesResponse?.map { Images(it.image) } ?: emptyList()


    private fun mapDate(datesResponse: List<DatesResponse>?): List<Dates> {
        return datesResponse?.map { Dates(Date(it.start.times(1000)), Date(it.end.times(1000))) }
            ?: emptyList()
    }
}