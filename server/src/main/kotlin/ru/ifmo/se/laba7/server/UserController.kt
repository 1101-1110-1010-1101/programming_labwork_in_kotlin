package ru.ifmo.se.laba7.server

import com.google.gson.Gson
import java.io.File
import java.security.MessageDigest

class UserController {
    val md = MessageDigest.getInstance("MD5")

    companion object {
        const val filepath = "F:\\ITMO\\Programming\\laba7\\server\\res\\users.json"
    }

    fun register(login: String, psw: String) {
        if (getUsers().any { it.login == login && it.pswrd_hash.contentEquals(md.digest(psw.toByteArray())) }) return
        File(filepath).appendText(User(login, md.digest(psw.toByteArray())).let { "\n" + Gson().toJson(it, User::class.java) })
    }

    fun login(login: String, psw: String) = getUsers().any { it.login == login && it.pswrd_hash.contentEquals(md.digest(psw.toByteArray())) }

    fun getUsers() = Gson().let { gson -> File(filepath).readLines().map { gson.fromJson(it, User::class.java) } }
}