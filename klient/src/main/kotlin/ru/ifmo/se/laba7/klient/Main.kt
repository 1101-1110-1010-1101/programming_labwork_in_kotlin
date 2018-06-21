package ru.ifmo.se.laba7.klient

import javafx.application.Application
import ru.ifmo.se.laba7.server.Astronaut

fun main(args: Array<String>){
    Application.launch(KlientForm::class.java)
//    val klient = Klient()
//    var response = klient.sendEcho("refresh").split("||")
//    val list = ArrayList<Astronaut>()
//    response.map { list.add(Astronaut.parseCsv(it)) }
//    print(list.size)
}