package ru.faizovr.afisha.presentation.mapper

import ru.faizovr.afisha.data.Date
import ru.faizovr.afisha.data.mapper.EntityMapper
import ru.faizovr.afisha.domain.model.Dates
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.model.EventListDataView
import java.text.SimpleDateFormat
import java.util.*

class EventListDataViewMapper : EntityMapper<EventShortInfo, EventListDataView> {
    override fun mapFromEntity(entity: EventShortInfo): EventListDataView =
        EventListDataView(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            date = mapDate(entity.dates),
            imageUrl = entity.images.first().image
        )

    private fun mapDate(dates: List<Dates>): String {
        val currentTime = Date().getCurrentDate()
        val date = dates.find { it.start >= currentTime }?.start ?: return ""
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("ru"))
        return simpleDateFormat.format(date)
    }
}