package ru.faizovr.afisha.core.domain.models

import java.util.*

class DateInterval(
    val dateFrom: Date,
    val dateTo: Date
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DateInterval

        if (dateFrom != other.dateFrom) return false
        if (dateTo != other.dateTo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dateFrom.hashCode()
        result = 31 * result + dateTo.hashCode()
        return result
    }
}