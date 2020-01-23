package ru.ruslan.weighttracker.fake

object FakePresentationLayerFactory {
    fun notEmptyString(): String {
        return "testString"
    }

    fun emptyString(): String {
        return ""
    }

    fun randomInt(): Int {
        return 1
    }
}