package ru.faizovr.afisha.presentation.mapper

import ru.faizovr.afisha.core.domain.models.DateInterval
import ru.faizovr.afisha.core.presentation.mapper.DateMapper
import ru.faizovr.afisha.data.TimeProvider
import ru.faizovr.afisha.domain.model.Event
import ru.faizovr.afisha.presentation.model.EventDetailDataView

class EventMapper(
    private val dateMapper: DateMapper
) {

    // TODO: 02.03.2021 убрать харкод места
    fun map(item: Event?): EventDetailDataView? =
        item?.let {
            EventDetailDataView(
                tittle = item.title,
                description = item.fullDescription,
                dateString = mapDateToString(item.dates),
                imageUrl = item.images.first(),
                place = "Цветной Бульвар"
            )
        }

    // TODO: 02.03.2021 переписать метод
    private fun mapDateToString(dates: List<DateInterval>): String {
        val currentTime = TimeProvider().getCurrentDate()
        val date = dates.find { it.dateFrom >= currentTime }?.dateFrom
        return dateMapper.mapDate(date)
    }
}
