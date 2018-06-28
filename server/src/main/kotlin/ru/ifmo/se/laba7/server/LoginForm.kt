package ru.ifmo.se.laba7.server

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.stage.Stage
import java.time.LocalDate
import java.util.*
import java.util.concurrent.Exchanger

class LoginForm : Application() {

  fun getResource(path: String) = javaClass.classLoader.getResource(path).openStream()

  companion object {
    @Volatile
    var ex = Exchanger<UserCollection>()
    @Volatile
    var message = ""
  }

  val col = UserCollection(DBHandler())
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

    val image1 = Image(getResource("image.png"))
    val image2 = Image(getResource("logo.png"))
    val err_image1 = Image(getResource("err_image(dark).png"))
    val err_image2 = Image(getResource("err_image(light).png"))
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

  fun createServerForm(): Scene {
    val expException = Alert(Alert.AlertType.WARNING).apply {
      title = "Not Valid Input"
      headerText = "Experience must be a number"
      contentText = "Reassign Experience as a number and try again"
    }
    val emptyException = Alert(Alert.AlertType.WARNING).apply {
      title = "Not Valid Input"
      headerText = "One of the fields (name or experience) are empy"
      contentText = "Add a value to it and try again"
    }
    val colIsEmpty = Alert(Alert.AlertType.INFORMATION).apply {
      title = "Information"
      headerText = "Collection is empty"
    }
    val mainFont = Font("Courier New", 16.0)
    val x = Label("x:").apply { font = mainFont }
    val y = Label("y:").apply { font = mainFont }
    val first = Button().apply { textProperty().bind(LocalesManager.getLocalizedBinding("FIRST")) }
    val last = Button().apply { textProperty().bind(LocalesManager.getLocalizedBinding("LAST")) }
    val addB = Button().apply { textProperty().bind(LocalesManager.getLocalizedBinding("ADD")) }
    val addIfMax = Button().apply { textProperty().bind(LocalesManager.getLocalizedBinding("ADDMAX")) }
    val removeGreater = Button().apply { textProperty().bind(LocalesManager.getLocalizedBinding("REMOVEGREAT")) }
    val nameField = TextField().apply {
      promptTextProperty().bind(LocalesManager.getLocalizedBinding("NAME"))
      prefWidth = 150.0
    }
    val coolnessIndex = TextField().apply {
      promptTextProperty().bind(LocalesManager.getLocalizedBinding("EXP"))
      prefWidth = 150.0
    }
    val color = ComboBox<Rectangle>(FXCollections.observableArrayList<Rectangle>(
        Rectangle(120.0, 10.0, Color.GREEN),
        Rectangle(120.0, 10.0, Color.RED),
        Rectangle(120.0, 10.0, Color.BLUE),
        Rectangle(120.0, 10.0, Color.YELLOW)
    )).apply {
      selectionModel.selectFirst()
      onAction = EventHandler {
        items[0] = Rectangle(120.0, 10.0, Color.GREEN)
        items[1] = Rectangle(120.0, 10.0, Color.RED)
        items[2] = Rectangle(120.0, 10.0, Color.BLUE)
        items[3] = Rectangle(120.0, 10.0, Color.YELLOW)
      }
      prefWidth = 150.0
    }
    val sliderX = Slider().apply {
      min = -750.0
      max = 750.0
      value = 0.0
      prefWidth = 150.0
    }
    val sliderY = Slider().apply {
      min = -750.0
      max = 750.0
      value = 0.0
      prefWidth = 150.0
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

    addB.prefWidth = 150.0
    server.children.add(addB)
    AnchorPane.setBottomAnchor(addB, 190.0)
    AnchorPane.setLeftAnchor(addB, 10.0)

    addIfMax.prefWidth = 150.0
    server.children.add(addIfMax)
    AnchorPane.setBottomAnchor(addIfMax, 160.0)
    AnchorPane.setLeftAnchor(addIfMax, 10.0)

    removeGreater.prefWidth = 150.0
    server.children.add(removeGreater)
    AnchorPane.setLeftAnchor(removeGreater, 10.0)
    AnchorPane.setBottomAnchor(removeGreater, 220.0)

    first.prefWidth = 150.0
    last.prefWidth = 150.0
    server.children.apply {
      add(first)
      add(last)
    }
    AnchorPane.setBottomAnchor(first, 280.0)
    AnchorPane.setBottomAnchor(last, 250.0)
    AnchorPane.setLeftAnchor(first, 10.0)
    AnchorPane.setLeftAnchor(last, 10.0)

    fun checkFields() {
      if (nameField.text.equals("") || coolnessIndex.text.equals(""))
        throw EmptyFieldException()
    }


    val columnName = TableColumn<Astronaut, String>().apply { textProperty().bind(LocalesManager.getLocalizedBinding("NAME")) }
    columnName.cellValueFactory = PropertyValueFactory<Astronaut, String>("name")
    columnName.isResizable = false
    columnName.prefWidth = 65.0
    val columnX = TableColumn<Astronaut, String>().apply { textProperty().bind(LocalesManager.getLocalizedBinding("COORS")) }
    columnX.cellValueFactory = PropertyValueFactory<Astronaut, String>("printCoors")
    columnX.prefWidth = 100.0
    columnX.isResizable = false
    val columnClns = TableColumn<Astronaut, Int>().apply { textProperty().bind(LocalesManager.getLocalizedBinding("EXP")) }
    columnClns.cellValueFactory = PropertyValueFactory<Astronaut, Int>("coolnessIndex")
    columnClns.prefWidth = 90.0
    columnClns.isResizable = false
    val columnColor = TableColumn<Astronaut, String>().apply { textProperty().bind(LocalesManager.getLocalizedBinding("COL")) }
    columnColor.cellValueFactory = PropertyValueFactory<Astronaut, String>("color")
    columnColor.prefWidth = 65.0
    val columnDate = TableColumn<Astronaut, LocalDate>().apply { textProperty().bind(LocalesManager.getLocalizedBinding("DATE")) }
    columnDate.prefWidth = Control.USE_COMPUTED_SIZE
    columnDate.cellValueFactory = PropertyValueFactory<Astronaut, LocalDate>("initDate")
    columnDate.prefWidth = 75.0
    val table = TableView<Astronaut>()
    table.isEditable = true
    table.columns.addAll(columnName, columnX, columnClns, columnColor, columnDate)
    server.children.add(table)
    table.prefHeight = 295.0
    table.prefWidth = 397.0
    AnchorPane.setLeftAnchor(table, 170.0)
    AnchorPane.setTopAnchor(table, 30.0)
    table.items = refreshTable()
    var editing = false
    fun editRow(a: Astronaut) {
      coolnessIndex.textProperty().addListener { _, _, newValue ->
        if (editing)
          try {
            table.selectionModel.selectedItem.coolnessIndex = newValue.toInt()
            table.refresh()
          } catch (n: NumberFormatException) { expException.showAndWait() }
      }
      nameField.textProperty().addListener { _, _, newValue ->
        if (editing) {
          table.selectionModel.selectedItem.name = newValue
          table.refresh()
        }
      }
      sliderX.valueProperty().addListener { _, _, newValue ->
        if (editing) {
          table.selectionModel.selectedItem.coordinates.x = newValue.toDouble()
          table.selectionModel.selectedItem.refreshCoors()
          table.refresh()
        }
      }
      sliderY.valueProperty().addListener { _, _, newValue ->
        if (editing) {
          table.selectionModel.selectedItem.coordinates.y = newValue.toDouble()
          table.selectionModel.selectedItem.refreshCoors()
          table.refresh()
        }
      }
      color.valueProperty().addListener { _, _, newValue ->
        if (editing) {
          table.selectionModel.selectedItem.color = Colors.fillToColors(newValue.fill)
          table.refresh()
        }
      }
    }
    table.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
      kotlin.run {
        try {
          editing = true
          editRow(newValue)
        } catch (i: IllegalStateException) { editing = false }
      }
    }


    addB.onAction = EventHandler {
      try {
        checkFields()
        val a = Astronaut(nameField.text, Astronaut.Coordinates(sliderX.value, sliderY.value), coolnessIndex.text.toInt(), Colors.fillToColors(color.value.fill))
        message = "add ${a.csv()}"
        table.items = refreshTable()
      } catch (n: NumberFormatException) {
        expException.showAndWait()
      } catch (e: EmptyFieldException) {
        emptyException.showAndWait()
      }
    }

    addIfMax.onAction = EventHandler {
      try {
        val a = Astronaut(nameField.text, Astronaut.Coordinates(sliderX.value, sliderY.value), coolnessIndex.text.toInt(), Colors.fillToColors(color.value.fill))
        message = "add_if_max ${a.csv()}"
        table.items = refreshTable()
      } catch (n: NumberFormatException) {
        expException.showAndWait()
      } catch (e: EmptyFieldException) {
        emptyException.showAndWait()
      }
    }

    removeGreater.onAction = EventHandler {
      try {
        val a = Astronaut(nameField.text, Astronaut.Coordinates(sliderX.value, sliderY.value), coolnessIndex.text.toInt(), Colors.fillToColors(color.value.fill))
        message = "remove_if_greater ${a.csv()}"
        table.items = refreshTable()
      } catch (n: NumberFormatException) {
        expException.showAndWait()
      } catch (e: EmptyFieldException) {
        emptyException.showAndWait()
      }
    }

    first.onAction = EventHandler {
      try {
        if (table.items.size == 0)
          throw NullPointerException()
        message = "remove_first ok?"
        table.items = refreshTable()
      } catch (n: NullPointerException) {
        colIsEmpty.showAndWait()
      }
    }

    last.onAction = EventHandler {
      try {
        if (table.items.size == 0)
          throw NullPointerException()
        message = "remove_last ok?"
        table.items = refreshTable()
      } catch (n: NullPointerException) {
        colIsEmpty.showAndWait()
      }
    }


    val menuBar = MenuBar()
    menuBar.prefWidth = 580.0
    val save_load = Menu().apply { textProperty().bind(LocalesManager.getLocalizedBinding("FILE")) }
    val saveM = MenuItem().apply { textProperty().bind(LocalesManager.getLocalizedBinding("SAVE")) }
    saveM.onAction = EventHandler { message = "save ok?" }
    val loadM = MenuItem().apply { textProperty().bind(LocalesManager.getLocalizedBinding("LOAD")) }
    loadM.onAction = EventHandler {
      message = "load ok?"
      table.items = refreshTable()
    }
    val lang = Menu().apply { textProperty().bind(LocalesManager.getLocalizedBinding("LANG")) }
    val rus = MenuItem("Русский")
    val eng = MenuItem("English")
    lang.items.addAll(rus, eng)
    rus.onAction = EventHandler {
      LocalesManager.selectAnotherLocale(Locale("ru", "RU"))
      table.items = refreshTable()
    }
    eng.onAction = EventHandler {
      LocalesManager.selectAnotherLocale(Locale("en", "US"))
      table.items = refreshTable()
    }
    save_load.items.addAll(saveM, loadM)
    menuBar.menus.addAll(save_load, lang)
    server.children.add(menuBar)

    val editMenu = ContextMenu()
    val edit = MenuItem("Edit")
    editMenu.items.add(edit)

    return Scene(server, 580.0, 335.0)
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

  fun refreshTable(): ObservableList<Astronaut> {
    val astrs = ex.exchange(col)
    return FXCollections.observableArrayList<Astronaut>(astrs)
  }
}
