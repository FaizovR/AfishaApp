package ru.faizovr.afisha.data

import java.util.*

class Date {
    fun currentTimeInMilliseconds(): String =
        Calendar.getInstance().timeInMillis.div(1000).toString()
}