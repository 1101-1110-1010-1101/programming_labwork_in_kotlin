package ru.ifmo.se.laba7.klient

import javafx.animation.FillTransition
import javafx.scene.paint.Color
import javafx.util.Duration
import ru.ifmo.se.laba7.server.Colors

class ColorChanging(c: KlientForm.AstroCircle, col: Color) {
    val circle = c
    val transition = FillTransition(Duration(2000.0), c, Colors.colorToFill(c.astronaut.color), col)
    val revTransition = FillTransition(Duration(2000.0), c, col, Colors.colorToFill(c.astronaut.color))

    fun start() {
        transition.isAutoReverse = true
        transition.setOnFinished { revTransition.play() }
        revTransition.isAutoReverse = true
        revTransition.setOnFinished { transition.play() }
        transition.play()
    }

    fun stop() {
        this.transition.stop()
        this.revTransition.stop()
        circle.fill = Colors.colorToFill(circle.astronaut.color)
    }
}