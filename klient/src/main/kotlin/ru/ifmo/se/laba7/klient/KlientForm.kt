package ru.ifmo.se.laba7.klient

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.event.EventHandler
import javafx.scene.Node
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
import java.io.FileInputStream

class KlientForm : Application() {
    val astronauts = FXCollections.observableArrayList<Astronaut>()

    companion object {
        @Volatile var message = ""
    }

    fun refresh() {
        val kl = Klient()

        val response = kl.sendEcho("refresh").split("||")
        val newAstronauts = response.map { Astronaut.parseCsv(it) }
        val toBeRemoved = astronauts.filter { !newAstronauts.contains(it) }
        val toBeAdded = newAstronauts.filter { !astronauts.contains(it) }
        astronauts.removeAll(toBeRemoved)
        astronauts.addAll(toBeAdded)
    }

    class AstroCircle(val astronaut: Astronaut): Circle(
            astronaut.coordinates.x / 3.125 + 450.0,
            astronaut.coordinates.y / 3.125 + 280.0,
            10.0)

    fun createKlientGUI(): Scene {
        val klient = AnchorPane()

        astronauts.addListener { c: ListChangeListener.Change<out Astronaut> ->
            while (c.next()) {
                klient.children.addAll(c.addedSubList.map { AstroCircle(it) })
                klient.children.removeIf { it is AstroCircle && c.removed.contains(it.astronaut) }
            }
        }
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
        refresh()
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

        roundButton.onAction = EventHandler { refresh() }

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
    }
}
