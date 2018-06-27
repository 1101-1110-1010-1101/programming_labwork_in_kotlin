package ru.ifmo.se.laba7.server

import com.google.gson.Gson
import org.mindrot.jbcrypt.BCrypt
import java.io.File

class UserController {

  companion object {
    const val filepath = "F:\\ITMO\\Programming\\laba7\\server\\res\\users.json"
  }

  fun register(login: String, psw: String) {
    if (getUsers().any { it.login == login }) return
    File(filepath).appendText(User(login, BCrypt.hashpw(psw, BCrypt.gensalt(10))).let { "\n" + Gson().toJson(it, User::class.java) })
  }

  fun login(login: String, psw: String) = getUsers().any { it.login == login && BCrypt.checkpw(psw, it.pswrdHash) }

  fun getUsers() = Gson().let { gson -> File(filepath).readLines().map { gson.fromJson(it, User::class.java) } }
}