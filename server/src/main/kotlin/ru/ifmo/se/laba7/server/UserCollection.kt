package ru.ifmo.se.laba7.server

import java.util.concurrent.ConcurrentLinkedDeque

class UserCollection(val db: DBHandler) : ConcurrentLinkedDeque<Astronaut>() {

  @Throws(NullPointerException::class)
  fun remove_first() {
    this.remove(max())
  }

  @Throws(NullPointerException::class)
  fun remove_last() {
    this.remove(min())
  }

  fun removeIfGreater(csv_srt: String) = CSVHandler.parseCsv(csv_srt).let { a -> this.removeIf { it > a } }

  fun addIfMax(csv_srt: String) = CSVHandler.parseCsv(csv_srt).let { a -> if (this.none { it > a }) this.add(a) }

  fun load() = db.getAstronauts().map { this.add(it) }

  fun save(){
    val array = ArrayList<Astronaut>()
    this.map { array.add(it) }
    db.saveAstronauts(array)
  }
}
