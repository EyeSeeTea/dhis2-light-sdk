package org.eyeseetea.dhis2.lightsdk.common

import io.ktor.util.date.Month
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private val GMT_TIMEZONE = TimeZone.getTimeZone("GMT")

@Suppress("FunctionName")
actual fun Datetime(timestamp: Long?): Datetime {
    return Calendar.getInstance(GMT_TIMEZONE, Locale.ROOT)!!.toDatetime(timestamp)
}

internal actual fun Datetime.Companion.format(datetime: Datetime): String {
    val date = Date(datetime.timestamp)

    return SimpleDateFormat(DHIS_DATE_FORMAT).format(date)
}

internal actual fun Datetime.Companion.parse(dateString: String): Datetime {
    return Datetime(SimpleDateFormat(DHIS_DATE_FORMAT).parse(dateString).time)
}

fun Datetime.toJVMDate(): Date {
    return Date(timestamp)
}

fun Datetime.Companion.fromJVMDate(jvmDate: Date): Datetime {
    return Datetime(jvmDate.time)
}

private fun Calendar.toDatetime(timestamp: Long?): Datetime {
    timestamp?.let { timeInMillis = it }

    val seconds = get(Calendar.SECOND)
    val minutes = get(Calendar.MINUTE)
    val hour = get(Calendar.HOUR_OF_DAY)
    val milliseconds = get(Calendar.MILLISECOND)
    val dayOfMonth = get(Calendar.DAY_OF_MONTH)

    val month = Month.from(get(Calendar.MONTH)).ordinal
    val year = get(Calendar.YEAR)

    return Datetime(
        year, month, dayOfMonth,
        hour, seconds, minutes, milliseconds, timeInMillis
    )
}
