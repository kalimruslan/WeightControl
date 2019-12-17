package ru.ruslan.weighttracker.data.datasource.api.exceptions

import java.io.IOException
import java.lang.StringBuilder

class NoConnectivityException(message: String) : IOException(message)
