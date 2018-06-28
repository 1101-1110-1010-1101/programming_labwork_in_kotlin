package ru.ifmo.se.laba7.server

import java.time.LocalDate

class CSVHandler {
  companion object {
    private val com = Comparator.comparingDouble<Astronaut> { it.distance }
    fun parseCsv(csv: String): Astronaut =
        csv.split(',').let { Astronaut(
            name = it[0],
            coordinates = Astronaut.Coordinates(it[1].substringBefore(" | ").toDouble(), it[1].substringAfter(" | ").toDouble()),
            coolnessIndex = it[2].toInt(),
            color = Colors.valueOf(it[3]),
            initDate = LocalDate.parse(it[4]))
        }
  }
}
