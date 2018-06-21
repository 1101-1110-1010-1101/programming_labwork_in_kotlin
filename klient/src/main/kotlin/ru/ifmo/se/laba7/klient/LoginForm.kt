package ru.ifmo.se.laba7.klient

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.stage.Stage
import java.util.concurrent.ConcurrentLinkedDeque

class LoginForm : Application() {

    companion object {
        @Volatile var message = ""
    }

    fun createKlientGUI(primaryStage: Stage): Scene {
        val klient = AnchorPane()

        return Scene(klient, 350.0, 350.0)
    }

    override fun start(primaryStage: Stage) {
        /*primaryStage.apply {
            title = "OR_ASS 1.001 alpha(klient)"
            scene = createLoginForm(this)
            isFullScreen = false
            isResizable = false
            show()
        }*/
        val klient = Klient()
        klient.sendEcho("refresh")
    }

}
