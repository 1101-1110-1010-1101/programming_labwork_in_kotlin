package ru.ifmo.se.laba7.server

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
    }
}
