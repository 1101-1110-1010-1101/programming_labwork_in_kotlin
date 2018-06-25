package ru.ifmo.se.laba7.server

import javafx.beans.binding.StringBinding
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import java.util.*

object LocalesManager {
    private val resources: ObjectProperty<ResourceBundle> = SimpleObjectProperty(ResourceBundle.getBundle("Strings", Locale("en", "US")))

    fun selectAnotherLocale(l: Locale) = resources.set(ResourceBundle.getBundle("Strings", l))

    fun getLocalizedBinding(key: String) = (object : StringBinding(){
        init { bind(resources) }
        override fun computeValue(): String = resources.get().getString(key)
    })
}