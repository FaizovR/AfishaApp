package ru.faizovr.afisha.presentation.mapper

import ru.faizovr.afisha.core.domain.models.DateInterval
import ru.faizovr.afisha.core.presentation.mapper.DateMapper
import ru.faizovr.afisha.data.TimeProvider
import ru.faizovr.afisha.domain.model.EventShort
import ru.faizovr.afisha.presentation.model.EventListDataView

// TODO: 02.03.2021 вынести в di mappers
class EventListDataViewMapper(
    private val dateMapper: DateMapper
) {

    fun mapFromEntity(entity: EventShort): EventListDataView =
        EventListDataView(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            date = mapDate(entity.dates),
            imageUrl = entity.images.first()
        )

    // TODO: 02.03.2021 переписать метод и вынести в model слой + кописаст EventMapper
    private fun mapDate(dates: List<DateInterval>): String {
        val currentTime = TimeProvider().getCurrentDate()
        val date = dates.find { it.dateFrom >= currentTime }?.dateFrom
        return dateMapper.mapDate(date)
    }
}