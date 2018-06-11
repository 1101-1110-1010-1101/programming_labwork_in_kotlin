
package ru.ifmo.se.laba7.server

import java.time.LocalDate
import java.util.*
import kotlin.Comparator

data class Astronaut (
        val name: String = "",
        val coordinates: Coordinates = Coordinates(Random().nextDouble() * Random().nextInt(1000), Random().nextDouble()* Random().nextInt(1000)),
        val coolnessIndex: Int = Random().nextInt(1000) + 500,
        val color: Colors = Colors.Blue,
        val initDate: LocalDate = LocalDate.now()
): Comparable<Astronaut> {
    val distance = Math.sqrt(Math.pow(this.coordinates.x, 2.0) + Math.pow(this.coordinates.y, 2.0))

    data class Coordinates(val x: Double, val y: Double) {
        override fun toString(): String = this.x.toString() + ',' + this.y.toString()
    }
    fun ClosedRange<Double>.random() = Random().nextDouble() +  start

    fun csv(): String = this.name + ',' + this.coordinates.toString() + ',' + this.coolnessIndex + ',' + this.color.toString() + ',' + this.initDate

    companion object {
        private val com = Comparator.comparingDouble<Astronaut> { it.distance }
        fun parseCsv(csv: String): Astronaut =
                csv.split(',').let { Astronaut(it[0], Coordinates(it[1].toDouble(), it[2].toDouble()), it[3].toInt(), Colors.stringToColor(it[4]), LocalDate.parse(it[5])) }
    }

    override fun compareTo(other: Astronaut) = com.compare(this, other)
}

