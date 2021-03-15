package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.core.domain.models.DateInterval
import ru.faizovr.afisha.data.TimeProvider
import ru.faizovr.afisha.data.model.*
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.domain.model.Event
import ru.faizovr.afisha.domain.model.EventPagedList
import ru.faizovr.afisha.domain.model.EventShort

// TODO: 02.03.2021 убрать отсюда timeProvider
class ApiMapper(
    private val timeProvider: TimeProvider = TimeProvider()
) {

    fun mapCategoriesList(list: List<CategoryApiItem>): List<Category> =
        list.map(this::map)

    fun map(item: CategoryApiItem): Category =
        Category(
            id = item.id,
            tag = item.slug,
            categoryName = item.name
        )

    fun map(item: DateIntervalApiItem): DateInterval =
        DateInterval(
            timeProvider.getDateFromSeconds(item.start),
            timeProvider.getDateFromSeconds(item.end)
        )

    fun mapDateIntervalsList(list: List<DateIntervalApiItem>): List<DateInterval> =
        list.map(this::map)

    fun map(item: EventDetailApiItem): Event =
        Event(
            id = item.id,
            title = item.title,
            fullDescription = item.body_text,
            dates = mapDateIntervalsList(item.dates),
            images = mapImagesList(item.images),
        )

    fun mapImagesList(item: List<ImageApiItem>): List<String> =
        item.map(ImageApiItem::image)

    // TODO: 02.03.2021 вынести в доменный слой эту логику
    fun map(item: EventListApiItem): EventPagedList {
        val next = item.next?.substringAfterLast(PAGE)?.substringBefore(AMPERSAND)
        val previous = item.previous?.substringAfterLast(PAGE)?.substringBefore(AMPERSAND)
        val events = item.results.map(this::map)
        return EventPagedList(
            count = item.count,
            nextPage = next,
            previousPage = previous,
            events = events
        )
    }

    fun map(item: EventShortApiItem): EventShort =
        EventShort(
            id = item.id,
            title = item.title,
            description = item.description,
            dates = mapDateIntervalsList(item.dates),
            images = mapImagesList(item.images)
        )

    companion object {
        private const val PAGE = "page="
        private const val AMPERSAND = "&"
    }
}