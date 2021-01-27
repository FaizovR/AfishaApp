package ru.faizovr.afisha.data.mapper

import ru.faizovr.afisha.data.TimeProvider
import ru.faizovr.afisha.data.model.DatesResponse
import ru.faizovr.afisha.domain.model.Dates

class DatesMapper(private val timeProvider: TimeProvider = TimeProvider()) :
    EntityMapper<DatesResponse, Dates> {
    override fun mapFromEntity(entity: DatesResponse): Dates =
        Dates(timeProvider.getDateFromSeconds(entity.start),
            timeProvider.getDateFromSeconds(entity.end))

    fun mapFromEntity(entity: List<DatesResponse>?): List<Dates> =
        entity?.map {
            mapFromEntity(it)
        }
            ?: emptyList()
}