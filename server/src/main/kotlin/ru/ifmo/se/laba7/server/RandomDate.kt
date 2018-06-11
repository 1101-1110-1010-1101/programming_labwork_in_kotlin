package ru.ifmo.se.laba7.server
import java.util.*

class RandomDate {
    var year = randBetween(1950, 1990)
    var gc = GregorianCalendar(year, 0, 0)
    var dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR))


    fun randBetween(start: Int, end:Int): Int = start + Random().nextInt(end - start)
    }

