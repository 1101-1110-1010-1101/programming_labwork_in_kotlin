package ru.ifmo.se.laba7.server

import javafx.application.Application
import kotlinx.coroutines.experimental.launch

@Throws(NullPointerException::class)
fun main(args: Array<String>) {

  val bridge = Bridge()


  // I`m a GUI thread!
  launch {
    Application.launch(LoginForm::class.java)
    System.exit(0)
  }
  // I`m the main thread!
  val ex = LoginForm.ex
  val collection = UserCollection()
  collection.load()
  collection.sorted()
  ex.exchange(collection)
  Connector.m = collection.map { it.csv() }.joinToString("||")
  // I`m the client listener!
  launch {
    bridge.run()
  }


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
            ex.exchange(collection)
            Connector.m = collection.map { it.csv() }.joinToString("||")
          }
          "add_if_max" -> {
            collection.addIfMax(argument)
            ex.exchange(collection)
            Connector.m = collection.map { it.csv() }.joinToString("||")
          }
          "remove_if_greater" -> {
            collection.removeIfGreater(argument)
            ex.exchange(collection)
            Connector.m = collection.map { it.csv() }.joinToString("||")
          }
          "remove_first" -> {
            collection.remove_first()
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
          }
          "load" -> {
            collection.clear()
            collection.load()
            ex.exchange(collection)
            Connector.m = collection.map { it.csv() }.joinToString("||")
          }
        }
      }
    }
  }
  while (true) {
  }
}