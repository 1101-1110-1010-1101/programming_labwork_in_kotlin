package ru.ifmo.se.laba7.server

class Connector {
  companion object {
    @Volatile
    var m = ""
  }

  public fun getM() = m
}