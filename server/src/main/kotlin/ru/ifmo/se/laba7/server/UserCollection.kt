package ru.ifmo.se.laba7.server

import com.google.gson.Gson
import java.time.LocalDate
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.LinkedBlockingQueue


class UserCollection : ConcurrentLinkedDeque<Astronaut>() {
    companion object {
        const val astronauts_datafile = "F:\\ITMO\\Programming\\laba7\\server\\res\\users.json"
    }



    val json: Gson = Gson()

    val initDate = LocalDate.now()

    fun info() = map { a ->
        "\n*** ${a.name} ***\nCoordinates: ${a.coordinates}\nExperience Index: ${a.coolnessIndex}" +
                "\nColor: ${a.color}\nInitialization Date: ${a.initDate}\n"
    }.joinToString().let {
        "*** INFO ***\nDatafile: $astronauts_datafile" +
                "\nInitialization Date: $initDate\nAstronauts in team: ${this.size}" +
                "\n*** Team list ***\n" + it + "\n*** End of Team list ***"
    }

    fun remove_first() = this.pollFirst()

    fun remove_last() = this.pollLast()

    fun removeIfGreater(json_str: String) = json.fromJson(json_str, Astronaut::class.java).let { a -> this.removeIf { it > a } }

    fun add(json_str: String) = this.add(json.fromJson(json_str, Astronaut::class.java))

    fun addIfMax(json_str: String) = json.fromJson(json_str, Astronaut::class.java).let { a -> if (this.none { it > a }) this.add(a) }
}