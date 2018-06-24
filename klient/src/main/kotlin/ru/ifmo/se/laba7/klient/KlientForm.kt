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
import java.net.SocketTimeoutException

class KlientForm : Application() {
    var anima = false
    fun getResource(path: String) = javaClass.classLoader.getResource(path).openStream()

    init {
        Font.loadFont(getResource("fa-regular-400.ttf"), 10.0)
        Font.loadFont(getResource("fa-solid-900.ttf"), 10.0)
    }
    val astronauts = FXCollections.observableArrayList<Astronaut>()



    class AstroCircle(public val astronaut: Astronaut): Circle(
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
        val selectionHelperCoordX = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=")).apply {
            selectionModel.selectFirst()
            prefWidth = 60.0
        }
        val selectionHelperCoordY = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=")).apply {
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

        // CMD
        val userString = TextField().apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        klient.children.add(userString)
        AnchorPane.setLeftAnchor(userString, 10.0)
        AnchorPane.setBottomAnchor(userString, 185.0)
        val panel0 = Label("  You can type smth down here").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel1 = Label("  I`m a little fancy command line").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel2 = Label("  Hello User!").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel3 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel4 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel5 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel6 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel7 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel8 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel9 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel10 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel11 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel12 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        val panel13 = Label("  .").apply {
            style = "-fx-background-color: Black; " +
                    "-fx-text-fill: White"
            prefWidth = 185.0
        }
        AnchorPane.setBottomAnchor(panel0, 205.0)
        AnchorPane.setBottomAnchor(panel1, 220.0)
        AnchorPane.setBottomAnchor(panel2, 235.0)
        AnchorPane.setBottomAnchor(panel3, 250.0)
        AnchorPane.setBottomAnchor(panel4, 265.0)
        AnchorPane.setBottomAnchor(panel5, 280.0)
        AnchorPane.setBottomAnchor(panel6, 295.0)
        AnchorPane.setBottomAnchor(panel7, 310.0)
        AnchorPane.setBottomAnchor(panel8, 325.0)
        AnchorPane.setBottomAnchor(panel9, 340.0)
        AnchorPane.setBottomAnchor(panel10, 355.0)
        AnchorPane.setBottomAnchor(panel11, 370.0)
        AnchorPane.setBottomAnchor(panel12, 385.0)
        AnchorPane.setBottomAnchor(panel13, 400.0)
        AnchorPane.setLeftAnchor(panel0, 10.0)
        AnchorPane.setLeftAnchor(panel1, 10.0)
        AnchorPane.setLeftAnchor(panel2, 10.0)
        AnchorPane.setLeftAnchor(panel3, 10.0)
        AnchorPane.setLeftAnchor(panel4, 10.0)
        AnchorPane.setLeftAnchor(panel5, 10.0)
        AnchorPane.setLeftAnchor(panel6, 10.0)
        AnchorPane.setLeftAnchor(panel7, 10.0)
        AnchorPane.setLeftAnchor(panel8, 10.0)
        AnchorPane.setLeftAnchor(panel9, 10.0)
        AnchorPane.setLeftAnchor(panel10, 10.0)
        AnchorPane.setLeftAnchor(panel11, 10.0)
        AnchorPane.setLeftAnchor(panel12, 10.0)
        AnchorPane.setLeftAnchor(panel13, 10.0)
        klient.children.addAll(panel0, panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, panel10, panel11, panel12, panel13)
        //
        fun writeToCmd(message: String){
            panel13.text = panel12.text
            panel12.text = panel11.text
            panel11.text = panel10.text
            panel10.text = panel9.text
            panel9.text = panel8.text
            panel8.text = panel7.text
            panel7.text = panel6.text
            panel6.text = panel5.text
            panel5.text = panel4.text
            panel4.text = panel3.text
            panel3.text = panel2.text
            panel2.text = panel1.text
            panel1.text = panel0.text
            panel0.text = "  " + message
            userString.text = ""
        }
        fun clear(){
            panel0.text = ""
            panel1.text = ""
            panel2.text = ""
            panel3.text = ""
            panel4.text = ""
            panel5.text = ""
            panel6.text = ""
            panel7.text = ""
            panel8.text = ""
            panel9.text = ""
            panel10.text = ""
            panel11.text = ""
            panel12.text = ""
            panel13.text = ""
        }


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

        klient.children.add(selectionHelperCoordX)
        AnchorPane.setBottomAnchor(selectionHelperCoordX, 35.0)
        AnchorPane.setLeftAnchor(selectionHelperCoordX, 135.0)

        klient.children.add(selectionHelperCoordY)
        AnchorPane.setBottomAnchor(selectionHelperCoordY, 5.0)
        AnchorPane.setLeftAnchor(selectionHelperCoordY, 135.0)

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

        fun filter(): List<Node> {
            var candidates = klient.children.filter { it is AstroCircle }
            when (selectionHelperCoordX.value) {
                "_" -> {}
                ">" -> { candidates = candidates.filter { it is AstroCircle && ((it.centerX - 450.0) * 3.125) > sliderX.value } }
                "<" -> { candidates = candidates.filter { it is AstroCircle && ((it.centerX - 450.0) * 3.125) < sliderX.value } }
                ">=" -> { candidates = candidates.filter { it is AstroCircle && ((it.centerX - 450.0) * 3.125) >= sliderX.value } }
                "<=" -> { candidates = candidates.filter { it is AstroCircle && ((it.centerX - 450.0) * 3.125) <= sliderX.value } }
            }
            when (selectionHelperCoordY.value) {
                "_" -> {}
                ">" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) > sliderY.value } }
                "<" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) < sliderY.value } }
                ">=" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) >= sliderY.value } }
                "<=" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) <= sliderY.value } }
            }
            when (selectionHelperName.value) {
                "_" -> {}
                ">" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name > nameField.text } }
                "<" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name < nameField.text } }
                ">=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name >= nameField.text } }
                "<=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name <= nameField.text } }
            }
            when (selectionHelperExp.value) {
                "_" -> {}
                ">" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex > coolnessIndex.text.toInt() } }
                "<" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex < coolnessIndex.text.toInt() } }
                ">=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex >= coolnessIndex.text.toInt() } }
                "<=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex <= coolnessIndex.text.toInt() } }
            }
            when (color.value){
                "Any" -> {}
                else -> candidates = candidates.filter { it is AstroCircle && it.astronaut.color == Colors.stringToColor(color.value) }
            }
            return candidates
        }
        fun refresh() {
            writeToCmd("Getting collection from server...")
            try {

                val kl = Klient()
                val response = kl.sendEcho("refresh").split("||")
                val newAstronauts = response.map { Astronaut.parseCsv(it) }
                val toBeRemoved = astronauts.filter { !newAstronauts.contains(it) }
                val toBeAdded = newAstronauts.filter { !astronauts.contains(it) }
                astronauts.removeAll(toBeRemoved)
                astronauts.addAll(toBeAdded)
                writeToCmd("Successfully.")
            } catch (s: SocketTimeoutException){
                writeToCmd("Sorry, server isn`t responding now")
                writeToCmd("Please, try again later")
            }
        }
        writeToCmd(".")
        refresh()
        val menuBar = MenuBar()
        menuBar.prefWidth = 720.0
        klient.children.add(menuBar)
        val save_load = Menu("File")
        val saveM = MenuItem("Save")
        val loadM = MenuItem("Load")
        save_load.items.addAll(saveM, loadM)
        menuBar.menus.addAll(save_load)

        val roundButton = Button("\uf2f1")
        val start = Button("\uf04b")
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
        start.style = baseCss
        AnchorPane.setTopAnchor(roundButton, 40.0)
        AnchorPane.setLeftAnchor(roundButton, 15.0)
        AnchorPane.setTopAnchor(start, 40.0)
        AnchorPane.setLeftAnchor(start, 105.0)
        klient.children.addAll(roundButton, start)

        roundButton.onMousePressed = EventHandler {
            roundButton.style = "$baseCss -fx-background-color: Green; "
        }
        roundButton.onMouseReleased = EventHandler { roundButton.style = baseCss }
        start.onMousePressed = EventHandler {
            start.style = "$baseCss -fx-background-color: Green; "
        }
        val animationUnits = ArrayList<ColorChanging>()
        fun startAnimation(){
            start.text = "\uf04d"
            anima = true
            val c = filter()
            writeToCmd("********************")
            writeToCmd("Animation Started")
            writeToCmd("Filter values:")
            if (selectionHelperName.value.equals("_"))
                writeToCmd("Name: Any")
            else
                writeToCmd("Name ${selectionHelperName.value} ${nameField.text}")
            writeToCmd("Color: ${color.value}")
            if (selectionHelperExp.value.equals("_"))
                writeToCmd("Experience: Any")
            else
                writeToCmd("Experience ${selectionHelperExp.value} ${coolnessIndex.text}")
            if (selectionHelperCoordX.value.equals("_"))
                writeToCmd("x-coordinate: Any")
            else writeToCmd("x-coordinate ${selectionHelperCoordX.value} ${levelX.text}")
            if (selectionHelperCoordY.value.equals("_"))
                writeToCmd("y-coordinate: Any")
            else writeToCmd("y-coordinate ${selectionHelperCoordY.value} ${levelY.text}")
            writeToCmd("Conditions were satisfied by:")
            writeToCmd("${c.size} astronauts")
            writeToCmd("********************")
            c.map {
                if (it is AstroCircle) {
                    val unit = ColorChanging(it, Color.BLACK)
                    animationUnits.add(unit)
                    unit.start()
                }
            }
        }
        fun stopAnimation(){
            start.text = "\uF04B"
            animationUnits.forEach { it.stop() }
            animationUnits.clear()
            anima = false
            writeToCmd("Animation stoped.")
        }
        fun interpreteCommand(command: String){
            when (command){
                "Hello OR_ASS" -> writeToCmd("Hello User!")
                "clear" -> clear()
                "refresh" -> refresh()
                "start" -> startAnimation()
                "stop" -> stopAnimation()
                }
        }

        userString.onAction = EventHandler {
            val message = userString.text
            writeToCmd(message)
            interpreteCommand(message)
        }

        start.onMouseReleased = EventHandler { start.style = baseCss }
        roundButton.onAction = EventHandler { refresh() }
        start.onAction = EventHandler {
            when (anima) {
                false -> startAnimation()
                true -> stopAnimation()
            }
        }
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
