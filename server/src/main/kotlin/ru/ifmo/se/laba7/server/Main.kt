package ru.ifmo.se.laba7.server

import javafx.application.Application
import kotlinx.coroutines.experimental.*

fun main(args: Array<String>) {

    val bridge = Bridge()


    // I`m a GUI thread!
    launch {
        Application.launch(LoginForm::class.java)
    }
    val ex = LoginForm.ex
    val collection = UserCollection()
    collection.load()
    collection.sorted()
    ex.exchange(collection)
    var forKlient = collection.map { it.csv() }.joinToString("||")
    println(forKlient)
    Connector.m = forKlient
    launch {
        bridge.run()
    }

    // I`m the main thread!


    // I`m a GUI listener!
    launch {
        while (true) {
            if (LoginForm.message != "") {
                val command = LoginForm.message.substringBefore(" ")
                val argument = LoginForm.message.substringAfter(" ")
                LoginForm.message = ""
                when (command) {
                    "add" -> {
                        collection.add(Astronaut.parseCsv(argument))
                        println("${argument.substringBefore(",")} is added to team")
                        ex.exchange(collection)
                        Connector.m = collection.map { it.csv() }.joinToString("||")
                    }
                    "add_if_max" -> {
                        println(collection.size)
                        collection.addIfMax(argument)
                        println(collection.size)
                        ex.exchange(collection)
                        Connector.m = collection.map { it.csv() }.joinToString("||")
                    }
                    "remove_if_greater" -> {
                        println(collection.size)
                        collection.removeIfGreater(argument)
                        println(collection.size)
                        ex.exchange(collection)
                        Connector.m = collection.map { it.csv() }.joinToString("||")
                    }
                    "remove_first" -> {collection.remove_first()
                        ex.exchange(collection)
                        Connector.m = collection.map { it.csv() }.joinToString("||")
                    }
                    "remove_last" -> {
                        collection.remove_last()
                        ex.exchange(collection)
                        Connector.m = collection.map { it.csv() }.joinToString("||")
                    }
                    "save" -> {
                        collection.save()
                        println("Data is saved to ${UserCollection.astronauts_datafile}")
                    }
                    "load" -> {
                        collection.clear()
                        collection.load()
                        println("Data is loaded from ${UserCollection.astronauts_datafile}")
                        ex.exchange(collection)
                        Connector.m = collection.map { it.csv() }.joinToString("||")
                    }
                }
            }
        }
    }
    while (true) {}
}