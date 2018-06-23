package ru.ifmo.se.laba7.klient

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Font
import javafx.stage.Stage
import ru.ifmo.se.laba7.server.Astronaut
import ru.ifmo.se.laba7.server.Colors
import java.io.FileInputStream

class KlientForm : Application() {
    fun getResource(path: String) = javaClass.classLoader.getResource(path).openStream()

    init {
        Font.loadFont(getResource("fa-regular-400.ttf"), 10.0)
        Font.loadFont(getResource("fa-solid-900.ttf"), 10.0)
    }
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
            10.0, Colors.colorToFill(astronaut.color))

    fun createKlientGUI(): Scene {
        val klient = AnchorPane()

        astronauts.addListener { c: ListChangeListener.Change<out Astronaut> ->
            while (c.next()) {
                klient.children.addAll(c.addedSubList.map { AstroCircle(it) })
                klient.children.removeIf { it is AstroCircle && c.removed.contains(it.astronaut) }
            }
        }
        val selectedImage = ImageView()
        val coordinatePane = Image(getResource("CoordinatePane.png"))
        selectedImage.apply {
            image = coordinatePane
            fitHeight = 500.0
            fitWidth = 500.0
        }
        AnchorPane.setRightAnchor(selectedImage, 10.0)
        AnchorPane.setBottomAnchor(selectedImage, 10.0)
        klient.children.add(selectedImage)
        refresh()

        val mainFont = Font("Courier New", 16.0)
        val filters = Label("Filters")
        filters.font = Font("Courier New", 24.0)
        val x = Label("x:").apply { font = mainFont }
        val y = Label("y:").apply { font = mainFont }
        val nameField = TextField().apply { promptText = "Name" }
        val coolnessIndex = TextField().apply { promptText = "Experience" }
        val color = ComboBox<String>(FXCollections.observableArrayList<String>("Any", "Green", "Red", "Blue", "Yellow")).apply {
            selectionModel.selectFirst()
        }
        val selectionHelperCoord = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=")).apply {
            selectionModel.selectFirst()
            prefWidth = 60.0
        }
        val selectionHelperName = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=")).apply {
            selectionModel.selectFirst()
            prefWidth = 60.0
        }
        val selectionHelperExp = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=")).apply {
            selectionModel.selectFirst()
            prefWidth = 60.0
        }
        val sliderX = Slider().apply {
            min = -750.0
            max = 750.0
            value = 0.0
        }
        val sliderY = Slider().apply {
            min = -750.0
            max = 750.0
            value = 0.0
        }
        val levelX = Label(sliderX.value.toString())
        val levelY = Label(sliderY.value.toString())

        klient.children.add(x)
        AnchorPane.setLeftAnchor(x, 10.0)
        AnchorPane.setBottomAnchor(x, 50.0)

        sliderX.prefWidth = 120.0
        klient.children.add(sliderX)
        AnchorPane.setBottomAnchor(sliderX, 35.0)
        AnchorPane.setLeftAnchor(sliderX, 10.0)

        klient.children.add(y)
        AnchorPane.setLeftAnchor(y, 10.0)
        AnchorPane.setBottomAnchor(y, 20.0)

        sliderY.prefWidth = 120.0
        klient.children.add(sliderY)
        AnchorPane.setLeftAnchor(sliderY, 10.0)
        AnchorPane.setBottomAnchor(sliderY, 5.0)

        klient.children.add(selectionHelperCoord)
        AnchorPane.setBottomAnchor(selectionHelperCoord, 20.0)
        AnchorPane.setLeftAnchor(selectionHelperCoord, 135.0)

        klient.children.add(levelX)
        AnchorPane.setLeftAnchor(levelX, 35.0)
        AnchorPane.setBottomAnchor(levelX, 50.0)

        klient.children.add(levelY)
        AnchorPane.setLeftAnchor(levelY, 35.0)
        AnchorPane.setBottomAnchor(levelY, 20.0)

        sliderX.valueProperty().addListener { _, _, new ->
            levelX.text = String.format("%.2f", new)
        }

        sliderY.valueProperty().addListener { _, _, new ->
            levelY.text = String.format("%.2f", new)
        }

        nameField.prefWidth = 120.0
        klient.children.add(nameField)
        AnchorPane.setBottomAnchor(nameField, 70.0)
        AnchorPane.setLeftAnchor(nameField, 10.0)

        klient.children.add(selectionHelperName)
        AnchorPane.setLeftAnchor(selectionHelperName, 135.0)
        AnchorPane.setBottomAnchor(selectionHelperName, 70.0)

        coolnessIndex.prefWidth = 120.0
        klient.children.add(coolnessIndex)
        AnchorPane.setLeftAnchor(coolnessIndex, 10.0)
        AnchorPane.setBottomAnchor(coolnessIndex, 100.0)

        klient.children.add(selectionHelperExp)
        AnchorPane.setLeftAnchor(selectionHelperExp, 135.0)
        AnchorPane.setBottomAnchor(selectionHelperExp, 100.0)

        klient.children.add(color)
        AnchorPane.setLeftAnchor(color, 10.0)
        AnchorPane.setBottomAnchor(color, 130.0)

        klient.children.add(filters)
        AnchorPane.setBottomAnchor(filters, 155.0)
        AnchorPane.setLeftAnchor(filters, 50.0)

        fun filter(){}

        val menuBar = MenuBar()
        menuBar.prefWidth = 720.0
        klient.children.add(menuBar)
        val save_load = Menu("File")
        val saveM = MenuItem("Save")
        val loadM = MenuItem("Load")
        save_load.items.addAll(saveM, loadM)
        menuBar.menus.addAll(save_load)

        val roundButton = Button("\uf2f1")
        val baseCss = "-fx-background-radius: 75em; " +
                "-fx-min-width: 75px; " +
                "-fx-min-height: 75px; " +
                "-fx-max-width: 75px; " +
                "-fx-max-height: 75px;" +
                "-fx-background-color: LightGreen; " +
                "-fx-font-family: 'Font Awesome 5 Free'; " +
                "-fx-font-size: 30px; " +
                "-fx-cursor: hand; "

        roundButton.style = baseCss
        AnchorPane.setTopAnchor(roundButton, 40.0)
        AnchorPane.setLeftAnchor(roundButton, 20.0)
        klient.children.add(roundButton)

        roundButton.onMousePressed = EventHandler {
            roundButton.style = "$baseCss -fx-background-color: Green; "
        }
        roundButton.onMouseReleased = EventHandler { roundButton.style = baseCss }
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
