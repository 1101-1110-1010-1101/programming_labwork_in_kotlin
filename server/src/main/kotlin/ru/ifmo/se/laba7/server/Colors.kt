package ru.ifmo.se.laba7.server

import javafx.scene.paint.Color
import javafx.scene.paint.Paint

enum class Colors {
  Red, Blue, Green, Yellow, Any;

  companion object {
    fun fillToColors(f: Paint) = when (f) {
      Color.GREEN -> Green
      Color.YELLOW -> Yellow
      Color.BLUE -> Blue
      Color.RED -> Red
      else -> Any
    }
  }

  override fun toString() = LocalesManager.getLocalizedString("COLORS_" + name.toUpperCase())
}
