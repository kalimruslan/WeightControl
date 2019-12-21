package ru.ruslan.weightracker.core

interface BaseMapper<in A, out B> {
    fun map(type: A?): B?
}