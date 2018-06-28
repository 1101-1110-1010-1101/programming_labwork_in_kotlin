
package ru.ifmo.se.laba7.server

import java.time.LocalDate
import java.util.*
import kotlin.Comparator

data class Astronaut (
        val name: String = "",
        val coordinates: Coordinates = Coordinates(Random().nextDouble() * Random().nextInt(1500) - 750, Random().nextDouble()* Random().nextInt(1500) - 750),
        val coolnessIndex: Int = Random().nextInt(1000) + 500,
        val color: Colors = Colors.Blue,
        val initDate: LocalDate = LocalDate.now()
): Comparable<Astronaut> {
    val distance = Math.sqrt(Math.pow(this.coordinates.x, 2.0) + Math.pow(this.coordinates.y, 2.0))
    public val printCoors = String.format("%.2f", coordinates.x) + " " + String.format("%.2f", coordinates.x)
    data class Coordinates(val x: Double, val y: Double) {
        override fun toString(): String = this.x.toString() + ',' + this.y.toString()
    }
    fun ClosedRange<Double>.random() = Random().nextDouble() +  start

    fun csv(): String = this.name + ',' + this.coordinates.toString() + ',' + this.coolnessIndex + ',' + this.color.toString() + ',' + this.initDate

    companion object {
        private val com = Comparator.comparingDouble<Astronaut> { it.distance }
        fun parseCsv(csv: String): Astronaut =
                csv.split(',').let { Astronaut(it[0], Coordinates(it[1].substringBefore(" | ").toDouble(), it[1].substringAfter(" | ").toDouble()), it[2].toInt(), Colors.stringToColor(it[3]), LocalDate.parse(it[4])) }
    }
    fun get_name() = this.name

    override fun compareTo(other: Astronaut) = com.compare(this, other)
}

