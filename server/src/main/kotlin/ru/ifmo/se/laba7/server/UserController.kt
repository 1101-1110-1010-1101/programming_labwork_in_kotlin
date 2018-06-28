package ru.ifmo.se.laba7.server

import com.google.gson.Gson
import org.mindrot.jbcrypt.BCrypt
import java.io.File

class UserController {

  companion object {
    val admins = "F:\\ITMO\\Programming\\laba7\\server\\src\\main\\resources\\admins.json"
    val users = "F:\\ITMO\\Programming\\laba7\\server\\src\\main\\resources\\users.json"
    fun userLogin(login: String, psw: String) = getUsers().any { it.login == login && BCrypt.checkpw(psw, it.pswrdHash) }
    fun getUsers() = Gson().let { gson -> File(users).readLines().map { gson.fromJson(it, User::class.java) } }
    fun registerUser(login: String, psw: String) {
      if (getUsers().any { it.login == login }) return
      File(users).appendText(User(login, BCrypt.hashpw(psw, BCrypt.gensalt(10))).let { "\n" + Gson().toJson(it, User::class.java) })
    }
  }

  fun register(login: String, psw: String) {
    if (getAdmins().any { it.login == login }) return
    File(admins).appendText(User(login, BCrypt.hashpw(psw, BCrypt.gensalt(10))).let { "\n" + Gson().toJson(it, User::class.java) })
  }

  fun login(login: String, psw: String) = getAdmins().any { it.login == login && BCrypt.checkpw(psw, it.pswrdHash) }





  fun getAdmins() = Gson().let { gson -> File(admins).readLines().map { gson.fromJson(it, User::class.java) } }


}
