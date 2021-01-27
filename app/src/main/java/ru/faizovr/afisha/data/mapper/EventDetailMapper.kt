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
            ageRestriction = entity.age_restriction ?: "",
            fullDescription = entity.body_text ?: "",
            categories = entity.categories ?: emptyList(),
            commentsCount = entity.comments_count ?: 0,
            dates = datesMapper.mapFromEntity(entity.dates),
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

    private fun mapImage(imagesResponse: List<ImagesResponse>?): List<String> =
        imagesResponse?.map(ImagesResponse::image) ?: emptyList()
}