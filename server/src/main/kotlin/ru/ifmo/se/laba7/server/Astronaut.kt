package ru.ifmo.se.laba7.server

import ru.ifmo.se.ridethemapping.RTM
import java.time.LocalDate
import java.util.*

@RTM.Table("astronauts")
data class Astronaut(
    var name: String = "",
    var coordinates: Coordinates = Coordinates(Random().nextDouble() * Random().nextInt(1000), Random().nextDouble() * Random().nextInt(1000)),
    var coolnessIndex: Int = Random().nextInt(1000) + 500,
    var color: Colors = Colors.Blue,
    var initDate: LocalDate = LocalDate.now(),
    @field:RTM.PrimaryKey
    var id: Int = 0
): Comparable<Astronaut>{
  val distance = Math.sqrt(Math.pow(this.coordinates.x, 2.0) + Math.pow(this.coordinates.y, 2.0))
  public var printCoors = String.format("%.2f", coordinates.x) + " " + String.format("%.2f", coordinates.y)

  fun refreshCoors() {
    printCoors = String.format("%.2f", coordinates.x) + " " + String.format("%.2f", coordinates.y)
  }

  data class Coordinates(var x: Double, var y: Double) {
    override fun toString(): String = this.x.toString() + " | " + this.y.toString()
  }

  fun csv(): String = this.name + ',' + this.coordinates.toString() + ',' + this.coolnessIndex + ',' + this.color.name + ',' + this.initDate

  fun get_name() = this.name

  override fun compareTo(other: Astronaut): Int {
    if (this.distance > other.distance)
      return 1
    else if (this.distance < other.distance)
      return -1
    else return 0
  }
}

