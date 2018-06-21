package ru.ifmo.se.laba7.klient

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.shape.Circle
import javafx.stage.Stage
import ru.ifmo.se.laba7.server.Astronaut
import java.awt.Paint
import java.io.FileInputStream
import java.util.concurrent.ConcurrentLinkedDeque

class KlientForm : Application() {

    companion object {
        @Volatile var message = ""
    }

    fun createKlientGUI(): Scene {

        val circles = ArrayList<Astronaut>()
        val kl = Klient()
        var response = kl.sendEcho("refresh").split("||")
        response.map { circles.add(Astronaut.parseCsv(it)) }


        val klient = AnchorPane()

        val selectedImage = ImageView()
        val coordinatePane = Image(FileInputStream("F:\\ITMO\\Programming\\laba7\\klient\\res\\CoordinatePane.png"))
        selectedImage.apply {
            image = coordinatePane
            fitHeight = 500.0
            fitWidth = 500.0
        }
        AnchorPane.setRightAnchor(selectedImage, 10.0)
        AnchorPane.setBottomAnchor(selectedImage, 10.0)
        klient.children.add(selectedImage)
        print(selectedImage.layoutX)
        print(selectedImage.layoutY)

        val menuBar = MenuBar()
        menuBar.prefWidth = 720.0
        klient.children.add(menuBar)
        val save_load = Menu("File")
        val saveM = MenuItem("Save")
        val loadM = MenuItem("Load")
        save_load.items.addAll(saveM, loadM)
        menuBar.menus.addAll(save_load)

        val roundButton = Button("R")
        roundButton.style = "-fx-background-radius: 50em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; " +
                "-fx-max-width: 50px; " +
                "-fx-max-height: 50px;" +
                "-fx-background-color: Green"
        AnchorPane.setBottomAnchor(roundButton, 40.0)
        AnchorPane.setLeftAnchor(roundButton, 40.0)
        klient.children.add(roundButton)
        // Just Example
//        val circle = Circle().apply {
//            centerX = 450.0
//            centerY = 280.0
//            radius = 10.0
//        }
        //Circle()
        //klient.children.add(circle)

        val astroCircles = ArrayList<Circle>()
        astroCircles.addAll(circles.map {
           Circle(it.coordinates.x/3 + 450.0, -it.coordinates.y/3 + 280.0, 10.0)
        })
        circles.map{
            println(it.coordinates.x.toString() + " " + it.coordinates.y.toString())
        }
        klient.children.addAll(astroCircles.map { it })

        return Scene(klient, 700.0, 530.0)
    }

    override fun start(primaryStage: Stage) {
        primaryStage.apply {
            title = "OR_ASS 1.001 alpha(klient)"
            scene = createKlientGUI()
            isFullScreen = false
            isResizable = false
            show()
        }
//        val klient = Klient()
//        klient.sendEcho("refresh")
    }

}
