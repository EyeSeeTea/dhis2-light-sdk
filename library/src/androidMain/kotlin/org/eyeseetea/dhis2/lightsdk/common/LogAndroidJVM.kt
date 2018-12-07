package org.eyeseetea.dhis2.lightsdk.common

internal actual fun writeLogMessage(message: String, logLevel: LogLevel) {
    println("[$logLevel]: $message")
}