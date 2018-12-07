package org.eyeseetea.dhis2.lightsdk.common

import io.ktor.util.date.InvalidTimestampException
import kotlin.js.Date

@Suppress("FunctionName")
actual fun Datetime(timestamp: Long?): Datetime {
    val jsDate = timestamp?.toDouble()?.let { Date(it) } ?: Date()

    logDebug("jsDate $jsDate from timestamp $timestamp")

    if (jsDate.getTime().isNaN()) throw InvalidTimestampException(timestamp!!)

    with(jsDate) {
        return Datetime(
            getUTCFullYear(),
            getUTCMonth(),
            getUTCDate(),
            getUTCHours(),
            getUTCMinutes(),
            getUTCSeconds(),
            getUTCMilliseconds(),
            getTime().toLong()
        )
    }
}

internal actual fun Datetime.Companion.format(datetime: Datetime): String {
    var jsDate: Date

    with(datetime) {
        jsDate = Date(Date.UTC(year, month, day, hour, minute, second, millisecond))
    }
    return jsDate.toISOString().replace("Z", "")
}

internal actual fun Datetime.Companion.parse(dateString: String): Datetime {
    val isoDateString = "${dateString}Z"
    val jsDate = Date(Date.parse(isoDateString))
    return Datetime(jsDate.getTime().toLong())
}

fun Datetime.toJSDate(): Date {
    return Date(timestamp)
}

fun Datetime.fromJSDate(jsDate: Date): Datetime {
    return Datetime(jsDate.getTime().toLong())
}