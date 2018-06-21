package ru.ifmo.se.laba7.server

import javafx.application.Application
import javafx.beans.Observable
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import java.io.*
import java.time.LocalDate
import java.util.concurrent.Exchanger

class LoginForm : Application() {

    companion object {
        @Volatile var ex = Exchanger<UserCollection>()
        @Volatile var message = ""
    }
    val col = UserCollection()
    private val userController = UserController()

    fun createLoginForm(primaryStage: Stage): Scene {

        val connect = Button("Connect")
        val register = Button("Registration")
        val login = TextField()
        val pswrd = PasswordField()

        val name = Label("Orbital Assistant").apply {
            textFill = Color.WHITE
            font = Font("Courier New", 28.0)
        }
        val name1 = Label("Professional ground \ncontrol software").apply {
            textFill = Color.WHITE
            font = Font("Courier New", 16.0)
        }
        val accesDenied = Label("Access denied!").apply {
            font = Font("Courier New", 18.0)
            textFill = Color.BLACK
        }

        val selectedImage = ImageView()



        register.onAction = EventHandler { userController.register(login.text, pswrd.text) }

        val image1 = Image(FileInputStream("F:\\ITMO\\Programming\\laba7\\server\\res\\image.png"))
        val image2 = Image(FileInputStream("F:\\ITMO\\Programming\\laba7\\server\\res\\logo.png"))
        val err_image1 = Image(FileInputStream("F:\\ITMO\\Programming\\laba7\\server\\res\\err_image(dark).png"))
        val err_image2 = Image(FileInputStream("F:\\ITMO\\Programming\\laba7\\server\\res\\err_image(light).png"))
        selectedImage.apply {
            image = image1
            fitHeight = 360.0
            fitWidth = 360.0
        }
        val loginform = AnchorPane()
        loginform.children.add(selectedImage)

        loginform.children.add(name)
        AnchorPane.setTopAnchor(name, 10.0)
        AnchorPane.setLeftAnchor(name, 10.0)
        loginform.children.add(name1)
        AnchorPane.setTopAnchor(name1, 40.0)
        AnchorPane.setLeftAnchor(name1, 10.0)

        loginform.children.add(connect)
        AnchorPane.setBottomAnchor(connect, 10.0)
        AnchorPane.setRightAnchor(connect, 10.0)

        loginform.children.add(register)
        AnchorPane.setBottomAnchor(register, 10.0)
        AnchorPane.setRightAnchor(register, AnchorPane.getRightAnchor(connect)!! + 70.0)

        login.promptText = "Login"
        loginform.children.add(login)
        AnchorPane.setTopAnchor(login, 265.0)
        AnchorPane.setRightAnchor(login, 10.0)

        pswrd.promptText = "Password"
        loginform.children.add(pswrd)
        AnchorPane.setTopAnchor(pswrd, 295.0)
        AnchorPane.setRightAnchor(pswrd, 10.0)

        val roundButton = Button()

        roundButton.style = "-fx-background-radius: 50em; " +
                "-fx-min-width: 20px; " +
                "-fx-min-height: 20px; " +
                "-fx-max-width: 20px; " +
                "-fx-max-height: 20px;" +
                "-fx-background-color: White"
        loginform.children.add(roundButton)
        AnchorPane.setBottomAnchor(roundButton, 5.0)
        AnchorPane.setLeftAnchor(roundButton, 5.0)
        connect.onAction = EventHandler {
            if (userController.login(login.text, pswrd.text)) {
                primaryStage.apply {
                    scene = createServerForm()
                }
            } else
                name.textFill = Color.BLACK
            name1.textFill = Color.BLACK
            when (selectedImage.image) {
                image1 -> selectedImage.image = err_image1
                image2 -> selectedImage.image = err_image2
            }
            if (!loginform.children.contains(accesDenied)) {
                loginform.children.add(accesDenied)
                AnchorPane.setTopAnchor(accesDenied, 247.0)
                AnchorPane.setRightAnchor(accesDenied, 7.0)
            }
            login.clear()
            pswrd.clear()
        }

        roundButton.onAction = EventHandler {
            if (selectedImage.image === image1 || selectedImage.image === err_image1) { // Станет белым
                selectedImage.image = image2
                roundButton.style = "-fx-background-radius: 50em; " +
                        "-fx-min-width: 20px; " +
                        "-fx-min-height: 20px; " +
                        "-fx-max-width: 20px; " +
                        "-fx-max-height: 20px;" +
                        "-fx-background-color: Black"
                if (loginform.children.contains(accesDenied))
                    accesDenied.textFill = Color.BLACK
                name.textFill = Color.BLACK
                name1.textFill = Color.BLACK
            } else {
                selectedImage.image = image1
                roundButton.style = "-fx-background-radius: 50em; " +
                        "-fx-min-width: 20px; " +
                        "-fx-min-height: 20px; " +
                        "-fx-max-width: 20px; " +
                        "-fx-max-height: 20px;" +
                        "-fx-background-color: WHITE"
                if (loginform.children.contains(accesDenied))
                    accesDenied.textFill = Color.WHITE
                name.textFill = Color.WHITE
                name1.textFill = Color.WHITE
            }
        }

        return Scene(loginform, 350.0, 350.0)
    }

    fun createServerForm(): Scene{
        val mainFont = Font("Courier New", 16.0)
        val x = Label("x:").apply { font = mainFont }
        val y = Label("y:").apply { font = mainFont }
        val remove = Label("Remove:").apply { font = mainFont }
        val first = Button("First")
        val last = Button("Last")
        val addB = Button("Add")
        val addIfMax = Button("Add If Max")
        val removeGreater = Button("Remove Greater")
        val nameField = TextField().apply { promptText = "Name" }
        val coolnessIndex = TextField().apply { promptText = "Experience" }
        val color = ComboBox<String>(FXCollections.observableArrayList<String>("Green", "Red", "Blue", "Yellow")).apply {
            selectionModel.selectFirst()
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
        val server = AnchorPane()
        // Add-block
        server.children.add(x)
        AnchorPane.setLeftAnchor(x, 10.0)
        AnchorPane.setBottomAnchor(x, 50.0)

        server.children.add(sliderX)
        AnchorPane.setBottomAnchor(sliderX, 35.0)
        AnchorPane.setLeftAnchor(sliderX, 10.0)

        server.children.add(y)
        AnchorPane.setLeftAnchor(y, 10.0)
        AnchorPane.setBottomAnchor(y, 20.0)

        server.children.add(sliderY)
        AnchorPane.setLeftAnchor(sliderY, 10.0)
        AnchorPane.setBottomAnchor(sliderY, 5.0)

        server.children.add(levelX)
        AnchorPane.setLeftAnchor(levelX, 35.0)
        AnchorPane.setBottomAnchor(levelX, 50.0)

        server.children.add(levelY)
        AnchorPane.setLeftAnchor(levelY, 35.0)
        AnchorPane.setBottomAnchor(levelY, 20.0)

        sliderX.valueProperty().addListener { _, _, new ->
            levelX.text = String.format("%.2f", new)
        }

        sliderY.valueProperty().addListener { _, _, new ->
            levelY.text = String.format("%.2f", new)
        }

        server.children.add(nameField)
        AnchorPane.setBottomAnchor(nameField, 70.0)
        AnchorPane.setLeftAnchor(nameField, 10.0)

        server.children.add(coolnessIndex)
        AnchorPane.setLeftAnchor(coolnessIndex, 10.0)
        AnchorPane.setBottomAnchor(coolnessIndex, 100.0)

        server.children.add(color)
        AnchorPane.setLeftAnchor(color, 10.0)
        AnchorPane.setBottomAnchor(color, 130.0)

        server.children.add(addB)
        AnchorPane.setBottomAnchor(addB, 160.0)
        AnchorPane.setLeftAnchor(addB, 10.0)

        server.children.add(addIfMax)
        AnchorPane.setBottomAnchor(addIfMax, 160.0)
        AnchorPane.setLeftAnchor(addIfMax, 50.0)

        server.children.add(removeGreater)
        AnchorPane.setLeftAnchor(removeGreater, 10.0)
        AnchorPane.setBottomAnchor(removeGreater, 190.0)

        server.children.apply {
            add(first)
            add(last)
            add(remove)
        }
        AnchorPane.setBottomAnchor(first, 220.0)
        AnchorPane.setBottomAnchor(last, 220.0)
        AnchorPane.setBottomAnchor(remove, 243.0)
        AnchorPane.setLeftAnchor(first, 10.0)
        AnchorPane.setLeftAnchor(last, 52.0)
        AnchorPane.setLeftAnchor(remove, 10.0)

        val columnName = TableColumn<Astronaut, String>("Name")
        columnName.cellValueFactory = PropertyValueFactory<Astronaut, String>("name")
        columnName.isResizable = false
        columnName.prefWidth = 65.0
        val columnX = TableColumn<Astronaut, String>("Coordinates")
        columnX.cellValueFactory = PropertyValueFactory<Astronaut, String>("printCoors")
        columnX.prefWidth = 100.0
        columnX.isResizable = false
        val columnClns = TableColumn<Astronaut, Int>("Experience")
        columnClns.cellValueFactory = PropertyValueFactory<Astronaut, Int>("coolnessIndex")
        columnClns.prefWidth = 90.0
        columnClns.isResizable = false
        val columnColor = TableColumn<Astronaut, Colors>("Color")
        columnColor.cellValueFactory = PropertyValueFactory<Astronaut, Colors>("color")
        columnColor.prefWidth = 65.0
        val columnDate = TableColumn<Astronaut, LocalDate>("Init Date")
        columnDate.cellValueFactory = PropertyValueFactory<Astronaut, LocalDate>("initDate")
        columnDate.prefWidth = 75.0
        val table = TableView<Astronaut>()
        table.isEditable = true
        table.columns.addAll(columnName, columnX, columnClns, columnColor, columnDate)
        server.children.add(table)
        table.prefHeight = 260.0
        table.prefWidth = 397.0
        AnchorPane.setLeftAnchor(table, 170.0)
        AnchorPane.setTopAnchor(table, 30.0)
        table.items = refreshTable()
        addB.onAction = EventHandler {
            val a = Astronaut(nameField.text, Astronaut.Coordinates(sliderX.value, sliderY.value), coolnessIndex.text.toInt(), Colors.stringToColor(color.value))
            print(a.coordinates)
            message = "add ${a.csv()}"
            table.items = refreshTable()
        }

        addIfMax.onAction = EventHandler {
            val a = Astronaut(nameField.text, Astronaut.Coordinates(sliderX.value, sliderY.value), coolnessIndex.text.toInt(), Colors.stringToColor(color.value))
            message = "add_if_max ${a.csv()}"
            table.items = refreshTable()
        }

        removeGreater.onAction = EventHandler {
            val a = Astronaut(nameField.text, Astronaut.Coordinates(sliderX.value, sliderY.value), coolnessIndex.text.toInt(), Colors.stringToColor(color.value))
            message = "remove_if_greater ${a.csv()}"
            table.items = refreshTable()
        }

        first.onAction = EventHandler { message = "remove_first ok?"
            table.items = refreshTable()
        }

        last.onAction = EventHandler { message = "remove_last ok?"
            table.items = refreshTable()
        }


        val menuBar = MenuBar()
        menuBar.prefWidth = 580.0
        val save_load = Menu("File")
        val saveM = MenuItem("Save")
        saveM.onAction = EventHandler { message = "save ok?" }
        val loadM = MenuItem("Load")
        loadM.onAction = EventHandler { message = "load ok?"
            table.items = refreshTable()
        }
        save_load.items.addAll(saveM, loadM)
        menuBar.menus.addAll(save_load)
        server.children.add(menuBar)

        return Scene(server, 580.0, 300.0)
    }

    override fun start(primaryStage: Stage) {
        primaryStage.apply {
            title = "OR_ASS 1.001 alpha(server)"
            scene = createLoginForm(this)
            isFullScreen = false
            isResizable = false
            show()
        }
    }

    fun refreshTable(): ObservableList<Astronaut>{
        val astrs = ex.exchange(col)
        return FXCollections.observableArrayList<Astronaut>(astrs)
    }
}
