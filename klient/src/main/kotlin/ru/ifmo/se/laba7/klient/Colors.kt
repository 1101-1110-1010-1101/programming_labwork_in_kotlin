package ru.ifmo.se.laba7.server

import javafx.scene.paint.Color
import javafx.scene.paint.Paint

enum class Colors {
    Red, Blue, Green, Yellow, Any;
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
            "Green" -> Color.LIGHTGREEN
            "Yellow" -> Color.YELLOW
            else -> Color.GREEN
        }
        fun fillToColors(f: Paint) = when (f) {
            Color.LIGHTGREEN -> Green
            Color.YELLOW -> Yellow
            Color.BLUE -> Blue
            Color.RED -> Red
            else -> Any
        }
    }
}
