package ru.faizovr.afisha.presentation.mapper

import ru.faizovr.afisha.data.Date
import ru.faizovr.afisha.data.mapper.EntityMapper
import ru.faizovr.afisha.domain.model.Dates
import ru.faizovr.afisha.domain.model.EventDetailInfo
import ru.faizovr.afisha.presentation.model.EventDetailInfoDataView
import java.text.SimpleDateFormat
import java.util.*

class EventDetailInfoMapper : EntityMapper<EventDetailInfo, EventDetailInfoDataView> {
    override fun mapFromEntity(entity: EventDetailInfo): EventDetailInfoDataView =
        EventDetailInfoDataView(
            tittle = entity.title,
            description = entity.fullDescription,
            dateString = mapDateToString(entity.dates),
            imageUrl = entity.images.first().image,
            place = "Цветной Бульвар"
        )

    private fun mapDateToString(dates: List<Dates>): String {
        val currentTime = Date().getCurrentDate()
        val date = dates.find { it.start >= currentTime }?.start ?: return ""
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("ru"))
        return simpleDateFormat.format(date)
    }
}