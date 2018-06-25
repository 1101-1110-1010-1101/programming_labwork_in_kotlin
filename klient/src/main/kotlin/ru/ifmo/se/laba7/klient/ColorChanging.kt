package ru.ifmo.se.laba7.klient

import javafx.animation.FillTransition
import javafx.scene.paint.Color
import javafx.util.Duration
import ru.ifmo.se.laba7.server.Colors

class ColorChanging(c: KlientForm.AstroCircle, col: Color, duration: Double) {
    val circle = c
    val transition = FillTransition(Duration(duration), c, Colors.colorToFill(c.astronaut.color), col)
    val revTransition = FillTransition(Duration(duration), c, col, Colors.colorToFill(c.astronaut.color))

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