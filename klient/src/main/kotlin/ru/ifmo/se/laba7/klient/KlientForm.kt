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
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.stage.Stage
import ru.ifmo.se.laba7.server.Astronaut
import ru.ifmo.se.laba7.server.Colors
import ru.ifmo.se.laba7.server.LocalesManager
import java.net.SocketTimeoutException
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

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
            10.0, Colors.colorToFill(astronaut.color)) {
        init {
            Tooltip.install(this, Tooltip(astronaut.name))
        }
    }

    fun createKlientGUI(): Scene {
        Locale.setDefault(Locale.ENGLISH)
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

        val set = Regex("set .*")
        var freq = 4000.0
        var anim_color = Color.BLACK
        val mainFont = Font("Courier New", 16.0)
        val filters = Label().apply { textProperty().bind(LocalesManager.getLocalizedBinding("FILTERS")) }
        filters.font = Font("Courier New", 24.0)
        val x = Label("x:").apply { font = mainFont }
        val y = Label("y:").apply { font = mainFont }
        val nameField = TextField().apply { promptTextProperty().bind(LocalesManager.getLocalizedBinding("NAME")) }
        val coolnessIndex = TextField().apply { promptTextProperty().bind(LocalesManager.getLocalizedBinding("EXP")) }
        val color = ComboBox<Rectangle>(FXCollections.observableArrayList<Rectangle>(
                Rectangle(10.0, 10.0, Color.WHITE),
                Rectangle(10.0, 10.0, Color.LIGHTGREEN),
                Rectangle(10.0, 10.0, Color.RED),
                Rectangle(10.0, 10.0, Color.BLUE),
                Rectangle(10.0, 10.0, Color.YELLOW)
                )).apply {
            selectionModel.selectFirst()
            onAction = EventHandler {
                items[0] = Rectangle(10.0, 10.0, Color.WHITE)
                items[1] = Rectangle(10.0, 10.0, Color.LIGHTGREEN)
                items[2] = Rectangle(10.0, 10.0, Color.RED)
                items[3] = Rectangle(10.0, 10.0, Color.BLUE)
                items[4] = Rectangle(10.0, 10.0, Color.YELLOW)
            }
            prefWidth = 30.0
        }
        val selectionHelperCoordX = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=", "=")).apply {
            selectionModel.selectFirst()
            prefWidth = 60.0
        }
        val selectionHelperCoordY = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=", "=")).apply {
            selectionModel.selectFirst()
            prefWidth = 60.0
        }
        val selectionHelperName = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=", "=")).apply {
            selectionModel.selectFirst()
            prefWidth = 60.0
        }
        val selectionHelperExp = ComboBox<String>(FXCollections.observableArrayList<String>("_", ">", "<", ">=", "<=", "=")).apply {
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

        // ************* CMD Messages *****************
        var getCol = "Getting collection from server..."
        var success = "Successfully."
        var notResp = "Server isn`t responding now"
        var tryLater = "Please, try again later"
        var animStop = "Animation stopped."
        var animStart = "Animation Started"
        var filterValues = "Filter values:"
        var nameAny = "Name: Any"
        var name = "Name "
        var colorAny = "Color: Any"
        var cmdColor = "Color: "
        var expAny = "Experience: Any"
        var cmdExp = "Experience "
        var xAny = "x-coordinate: Any"
        var cmdX = "x-coordinate "
        var yAny = "y-coordinate: Any"
        var cmdY = "y-coordinate "
        var initAny = "Init Date: Any"
        var cmdInit = "Init Date: "
        var satisfied = "Conditions were satisfied by:"
        var cmdAstro = " astronauts"
        var expNum = "Experience should be a number"
        var notNum = " is not a number"
        var notCol = ": unknown color"
        var notOper = ": unknown operator"
        var patternX = "Pattern: set x operator number"
        var patternY = "Pattern: set y operator number"
        var patternDate = "Date Pattern: YYYY-MM-DD"
        var hello = "Hello User!"
        fun changeCmdLang(l: String) {
            when (l) {
                "eng" -> {
                    getCol = "Getting collection from server..."
                    success = "Successfully."
                    notResp = "Server isn`t responding now"
                    tryLater = "Please, try again later"
                    animStop = "Animation stopped."
                    animStart = "Animation Started"
                    filterValues = "Filter values:"
                    nameAny = "Name: Any"
                    name = "Name "
                    colorAny = "Color: Any"
                    cmdColor = "Color: "
                    expAny = "Experience: Any"
                    cmdExp = "Experience "
                    xAny = "x-coordinate: Any"
                    cmdX = "x-coordinate "
                    yAny = "y-coordinate: Any"
                    cmdY = "y-coordinate "
                    initAny = "Init Date: Any"
                    cmdInit = "Init Date: "
                    satisfied = "Conditions were satisfied by:"
                    cmdAstro = " astronauts"
                    expNum = "Experience should be a number"
                    notNum = " is not a number"
                    notCol = ": unknown color"
                    notOper = ": unknown operator"
                    patternX = "Pattern: set x operator number"
                    patternY = "Pattern: set y operator number"
                    patternDate = "Date Pattern: YYYY-MM-DD"
                    hello = "Hello User!"
                }
                "rus" -> {
                    getCol = "Получаем коллекцию сервера..."
                    success = "Удачно."
                    notResp = "Сервер не отвечает"
                    tryLater = "Пожалуйста, попробуйте позже"
                    animStop = "Анимация остановлена."
                    animStart = "Анимация начата"
                    filterValues = "Значения фильтров:"
                    nameAny = "Имя: Любое"
                    name = "Имя "
                    colorAny = "Цвет: Любой"
                    cmdColor = "Цвет: "
                    expAny = "Опыт: Любой"
                    cmdExp = "Опыт "
                    xAny = "x-координата: Любая"
                    cmdX = "x-координата "
                    yAny = "y-координата: Любая"
                    cmdY = "y-координата "
                    initAny = "Дата: Любая"
                    cmdInit = "Дата: "
                    satisfied = "Условия были удовлетворены:"
                    cmdAstro = " космонавтами"
                    expNum = "Опыт должен быть числом"
                    notNum = " не является числом"
                    notCol = ": неизвестный цвет"
                    notOper = ": неизвестный оператор"
                    patternX = "Формат: set x оператор число"
                    patternY = "Формат: set y оператор число"
                    patternDate = "Формат дфты: YYYY-MM-DD"
                    hello = "Привет пользователь!"
                }
            }
        }
        // ********************************************

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
        val date = DatePicker().apply { prefWidth = 130.0 }
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
            panel0.text = "  $message"
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

        klient.children.add(date)
        AnchorPane.setLeftAnchor(date, 65.0)
        AnchorPane.setBottomAnchor(date, 129.0)

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
                "=" -> { candidates = candidates.filter { it is AstroCircle && ((it.centerX - 450.0) * 3.125) == sliderX.value } }
            }
            when (selectionHelperCoordY.value) {
                "_" -> {}
                ">" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) > sliderY.value } }
                "<" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) < sliderY.value } }
                ">=" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) >= sliderY.value } }
                "<=" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) <= sliderY.value } }
                "=" -> { candidates = candidates.filter { it is AstroCircle && (-(it.centerY - 280.0) * 3.125) == sliderY.value } }
            }
            when (selectionHelperName.value) {
                "_" -> {}
                ">" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name > nameField.text } }
                "<" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name < nameField.text } }
                ">=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name >= nameField.text } }
                "<=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name <= nameField.text } }
                "=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.name == nameField.text } }
            }
            when (selectionHelperExp.value) {
                "_" -> {}
                ">" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex > coolnessIndex.text.toInt() } }
                "<" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex < coolnessIndex.text.toInt() } }
                ">=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex >= coolnessIndex.text.toInt() } }
                "<=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex <= coolnessIndex.text.toInt() } }
                "=" -> { candidates = candidates.filter { it is AstroCircle && it.astronaut.coolnessIndex == coolnessIndex.text.toInt() } }
            }
            when (Colors.fillToColors(color.value.fill)){
                Colors.Any -> {}
                else -> candidates = candidates.filter { it is AstroCircle && it.astronaut.color == Colors.fillToColors(color.value.fill) }
            }
            when (date.value){
                null -> {}
                else -> candidates = candidates.filter { it is AstroCircle && it.astronaut.initDate == date.value }
            }
            return candidates
        }
        fun refresh() {
            writeToCmd(getCol)
            try {

                val kl = Klient()
                val response = kl.sendEcho("refresh").split("||")
                val newAstronauts = response.map { Astronaut.parseCsv(it) }
                val toBeRemoved = astronauts.filter { !newAstronauts.contains(it) }
                val toBeAdded = newAstronauts.filter { !astronauts.contains(it) }
                astronauts.removeAll(toBeRemoved)
                astronauts.addAll(toBeAdded)
                writeToCmd(success)
            } catch (s: SocketTimeoutException){
                writeToCmd(notResp)
                writeToCmd(tryLater)
            }
        }
        writeToCmd(".")
        refresh()
        val menuBar = MenuBar()
        menuBar.prefWidth = 720.0
        klient.children.add(menuBar)
        val lang = Menu().apply { textProperty().bind(LocalesManager.getLocalizedBinding("LANG")) }
        val eng = MenuItem("English").apply {
            onAction = EventHandler {
                LocalesManager.selectAnotherLocale(Locale("en", "US"))
                changeCmdLang("eng")
            }
        }
        val rus = MenuItem("Русский").apply {
            onAction = EventHandler {
                LocalesManager.selectAnotherLocale(Locale("ru", "RU"))
                changeCmdLang("rus")
            }
        }
        lang.items.addAll(eng, rus)
        menuBar.menus.addAll(lang)

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
        start.onMouseReleased = EventHandler { start.style = baseCss }
        val animationUnits = ArrayList<ColorChanging>()
        fun stopAnimation(){
            start.text = "\uF04B"
            animationUnits.forEach { it.stop() }
            animationUnits.clear()
            anima = false
            writeToCmd(animStop)
        }
        fun startAnimation(){
            try{
            start.text = "\uf04d"
            anima = true
            val c = filter()
            writeToCmd("********************")
            writeToCmd(animStart)
            writeToCmd(filterValues)
            if (selectionHelperName.value.equals("_"))
                writeToCmd(nameAny)
            else{
                writeToCmd(name + "${selectionHelperName.value} ${nameField.text}")}
            if (color.value.fill == Color.WHITE)
                writeToCmd(colorAny)
            else
                writeToCmd(cmdColor + "${Colors.fillToColors(color.value.fill)}")
            if (selectionHelperExp.value.equals("_"))
                writeToCmd(expAny)
            else
                writeToCmd(cmdExp + "${selectionHelperExp.value} ${coolnessIndex.text}")
            if (selectionHelperCoordX.value.equals("_"))
                writeToCmd(xAny)
            else writeToCmd(cmdX + "${selectionHelperCoordX.value} ${levelX.text}")
            if (selectionHelperCoordY.value.equals("_"))
                writeToCmd(yAny)
            else writeToCmd(cmdY + "${selectionHelperCoordY.value} ${levelY.text}")
            if (date.value == null)
                writeToCmd(initAny)
            else (writeToCmd(cmdInit + "${date.value}"))
            writeToCmd(satisfied)
            writeToCmd("${c.size}" + cmdAstro)
            writeToCmd("********************")
            if (c.isEmpty())
                stopAnimation()
            c.map {
                if (it is AstroCircle) {
                    val unit = ColorChanging(it, anim_color, freq)
                    animationUnits.add(unit)
                    unit.start()
                }
            }} catch (n: NumberFormatException) {
                writeToCmd(expNum)
                stopAnimation()
            }
        }
        fun setResolver(com: String){
            val args = com.split(Regex(" "), 4)
            when (args[1]) {
                "freq" -> {
                    try {
                        freq = args[3].toDouble() * 1000
                        writeToCmd("freq = $freq")
                    } catch (n: NumberFormatException) { writeToCmd(args[3] + notNum) }
                }
                "anim_color" -> {
                    try {
                        anim_color = Color.valueOf(args[3])
                        writeToCmd("anim_color = ${args[3]}")
                    } catch (i: IllegalArgumentException) { writeToCmd(args[3] + notCol) }
                }
                "name" -> {
                    try {
                        if (args[2].equals("_")) {
                            selectionHelperName.value = "_"
                            return
                        }
                        nameField.text = args[3]
                        if (!(args[2] in selectionHelperName.items))
                            throw IllegalArgumentException()
                        else selectionHelperName.value = args[2]
                    } catch (i: IllegalArgumentException) { writeToCmd(args[2] + notOper) }
                }
                "exp" -> {
                    try {
                        if (args[2].equals("_")) {
                            selectionHelperExp.value = "_"
                            return
                        }
                        coolnessIndex.text = args[3]
                        if (!(args[2] in selectionHelperExp.items))
                            throw IllegalArgumentException()
                        else selectionHelperExp.value = args[2]
                    } catch (i: IllegalArgumentException) { writeToCmd(args[2] + notOper) }
                }
                "x" -> {
                    try {
                        if (args[2].equals("_")) {
                            selectionHelperCoordX.value = "_"
                            return
                        }
                        sliderX.value = args[3].toDouble()
                        if (!(args[2] in selectionHelperCoordX.items))
                            throw IllegalArgumentException()
                        else selectionHelperCoordX.value = args[2]
                    } catch (i: IllegalArgumentException) { writeToCmd(patternX) }
                }
                "y" -> {
                    try {
                        if (args[2].equals("_")) {
                            selectionHelperCoordY.value = "_"
                            return
                        }
                        sliderY.value = args[3].toDouble()
                        if (!(args[2] in selectionHelperCoordY.items))
                            throw IllegalArgumentException()
                        else selectionHelperCoordY.value = args[2]
                    } catch (i: IllegalArgumentException) { writeToCmd(patternY) }
                }
                "color" -> {
                    try {
                        if (args[2].equals("_")) {
                            color.value = color.items[0]
                            return
                        }
                        if (!(args[3] in arrayOf("Green", "Red", "Blue", "Yellow")))
                            throw IllegalArgumentException()
                        else color.value = Rectangle(10.0, 10.0, Colors.colorToFill(Colors.stringToColor(args[3])))
                    } catch (i: IllegalArgumentException) { writeToCmd(args[3] + notCol) }
                }
                "date" -> {
                    try {
                        if (args[3].equals("_")) {
                            date.value = null
                            return
                        }
                        date.value = LocalDate.parse(args[3])
                    } catch (d: DateTimeParseException) { writeToCmd(patternDate) }
                }
            }
        }
        fun interpreteCommand(command: String){
            when (command){
                "Hello OR_ASS" -> writeToCmd(hello)
                "clear" -> clear()
                "refresh" -> refresh()
                "start" -> startAnimation()
                "stop" -> stopAnimation()
                else -> {
                    if (set.matches(command))
                        setResolver(command)
                    }
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
