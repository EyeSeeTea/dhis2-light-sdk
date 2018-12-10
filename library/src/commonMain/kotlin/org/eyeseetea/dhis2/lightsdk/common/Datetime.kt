package org.eyeseetea.dhis2.lightsdk.common

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.DoubleDescriptor

/**
 * The class Datetime represents a specific instant
 * in time, with millisecond precision.
 *
 * @property year property number indicating the year
 * @property month property number indicating the
 * month. This is a calendar-specific value. The first month of
 * the year in the Gregorian and Julian calendars is JANUARY which is 0
 * @property day property number indicating the day of the month
 * The first day of the month has value 1.
 * @property hour property number indicating the hour of the day. It is used for the 24-hour clock
 * E.g., at 10:04:15.250 PM the hour is 22.
 * @property minute property number indicating the
 * minute within the hour. E.g., at 10:04:15.250 PM the minute is 4
 * @property second property number the second within the minute
 * E.g., at 10:04:15.250 PM the second is 15
 * @property millisecond property number indicating the millisecond within the minute
 * E.g., at 10:04:15.250 PM the millisecond is 250.
 * @property timestamp property number indicating the time as UTC milliseconds from the epoch
 * @constructor Creates a Datetime.
 */
@Serializable
data class Datetime internal constructor(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val millisecond: Number,
    val timestamp: Long
) : Comparable<Datetime> {

    override fun compareTo(other: Datetime): Int = timestamp.compareTo(other.timestamp)

    @Serializer(forClass = Datetime::class)
    companion object : KSerializer<Datetime> {

        override val descriptor: SerialDescriptor = DoubleDescriptor

        override fun serialize(output: Encoder, obj: Datetime) {
            output.encodeString(obj.format())
        }

        override fun deserialize(input: Decoder): Datetime {
            return Datetime.parse(input.decodeString())
        }
    }

    override fun toString(): String {
        return format()
    }
}

@Suppress("FunctionName")
expect fun Datetime(timestamp: Long? = null): Datetime

internal expect fun Datetime.Companion.parse(dateString: String): Datetime
internal expect fun Datetime.format(): String
