package ru.faizovr.afisha.data

import java.util.*
import java.util.Date

class Date {
    fun currentTimeInMilliseconds(): Long =
        System.currentTimeMillis().div(1000)

    fun getCurrentDate(): Date =
        Calendar.getInstance().time
}