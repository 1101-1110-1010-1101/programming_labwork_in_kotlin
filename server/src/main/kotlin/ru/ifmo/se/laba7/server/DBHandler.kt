package ru.ifmo.se.laba7.server

import ru.ifmo.se.ridethemapping.PrimaryKey
import ru.ifmo.se.ridethemapping.RTM
import java.time.LocalDate

class DBHandler {

  val rtm = RTM("jdbc:postgresql://localhost:5432/laba8", "keker", "qwerty")
  val id = PrimaryKey()

  fun getAstronauts(): ArrayList<Astronaut> {
    val result = ArrayList<Astronaut>()
    val list = rtm.select(Astronaut::class.java)
    for (l in list)
      result.add(Astronaut(
          l[2],
          Astronaut.Coordinates(l[3].substringBefore(" | ").toDouble(), l[3].substringAfter(" | ").toDouble()),
          l[4].toInt(),
          Colors.valueOf(l[5]),
          LocalDate.parse(l[6]),
          l[7].toInt()))
    return result
  }
  fun saveAstronauts(astronauts: ArrayList<Astronaut>)  {
    rtm.delete(Astronaut::class.java)
    id.refershKey()
    for (astronaut in astronauts) {
      astronaut.id = id.getNextKey()
      rtm.insert(astronaut)
    }
  }
}
