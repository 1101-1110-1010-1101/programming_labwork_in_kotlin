package ru.ifmo.se.laba7.server

import javafx.scene.paint.Color

enum class Colors {
    Red, Blue, Green, Yellow;
    companion object {
        fun stringToColor(s: String): Colors {
            when (s) {
                "Red" -> return Red
                "Blue" -> return Blue
                "Green" -> return Green
                "Yellow" -> return Yellow
                else -> return Green
            }
        }
        fun colorToFill(c: Colors) = when (c.toString()) {
            "Red" -> Color.RED
            "Blue" -> Color.BLUE
            "Green" -> Color.GREEN
            "Yellow" -> Color.YELLOW
            else -> Color.GREEN
        }
    }
}
