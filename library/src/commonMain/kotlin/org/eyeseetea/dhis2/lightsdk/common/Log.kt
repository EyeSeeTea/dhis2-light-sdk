package org.eyeseetea.dhis2.lightsdk.common

enum class LogLevel {
    DEBUG, WARN, ERROR
}

internal expect fun writeLogMessage(message: String, logLevel: LogLevel)

internal fun logDebug(message: String) = writeLogMessage(message, LogLevel.DEBUG)
internal fun logWarn(message: String) = writeLogMessage(message, LogLevel.WARN)
internal fun logError(message: String) = writeLogMessage(message, LogLevel.ERROR)
