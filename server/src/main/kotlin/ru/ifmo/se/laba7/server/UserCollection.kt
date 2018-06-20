package ru.ifmo.se.laba7.server

import java.io.File
import java.time.LocalDate
import java.util.concurrent.ConcurrentLinkedDeque

class UserCollection : ConcurrentLinkedDeque<Astronaut>() {
    companion object {
        const val astronauts_datafile = "F:\\ITMO\\Programming\\laba7\\server\\res\\astronauts.csv"
    }

    val initDate = LocalDate.now()

    fun info() = map { a ->
        "\n*** ${a.name} ***\nCoordinates: ${a.coordinates}\nExperience Index: ${a.coolnessIndex}" +
                "\nColor: ${a.color}\nInitialization Date: ${a.initDate}\n"
    }.joinToString().let {
        "*** INFO ***\nDatafile: $astronauts_datafile" +
                "\nInitialization Date: $initDate\nAstronauts in team: ${this.size}" +
                "\n*** Team list ***\n" + it + "\n*** End of Team list ***"
    }

    fun remove_first() {
        println("${this.max()?.name} is removed from the team")
        this.remove(max())
    }

    fun remove_last() {
        println("${this.min()?.name} is removed from the team")
        this.remove(min())
    }

    fun removeIfGreater(csv_srt: String) = Astronaut.parseCsv(csv_srt).let { a -> this.removeIf { it > a } }

    fun addIfMax(csv_srt: String) = Astronaut.parseCsv(csv_srt).let { a -> if (this.none { it > a }) this.add(a) }

    fun load() = File(astronauts_datafile).readLines().map { line -> this.add(Astronaut.parseCsv(line)) }

    fun save() = File(astronauts_datafile).let { f -> f.writeText(this.map { it.csv() }.joinToString("\n")) }
}