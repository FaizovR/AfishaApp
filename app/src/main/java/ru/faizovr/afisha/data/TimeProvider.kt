package ru.faizovr.afisha.data

import java.util.*

class TimeProvider {
    private fun currentTimeInMilliseconds(): Long =
        System.currentTimeMillis()

    fun currentTimeInSeconds(): Long =
        currentTimeInMilliseconds().div(1000)

    fun getDateFromSeconds(seconds: Long): Date =
        Date(seconds.times(1000))

    fun getCurrentDate(): Date =
        Date(currentTimeInMilliseconds())
}