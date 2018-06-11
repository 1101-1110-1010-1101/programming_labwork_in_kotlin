package ru.ifmo.se.laba7.server

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.stage.*
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCombination
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import java.io.*

class LoginForm : Application() {
    private val userController = UserController()

    override fun start(primaryStage: Stage) {



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

        primaryStage.apply {
            title = "OR_ASS 1.001 alpha(server) LOGIN"
            scene = Scene(loginform, 350.0, 350.0)
            isFullScreen = false
            isResizable = false
            show()
        }

        connect.onAction = EventHandler {
            if (userController.login(login.text, pswrd.text)) {
                println("Success")
                loginform.children.clear()
                primaryStage.fullScreenExitKeyCombination = KeyCombination.NO_MATCH
                primaryStage.isFullScreen = true
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
    }
}
