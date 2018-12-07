package org.eyeseetea.dhis2.lightsdk.common

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.DoubleDescriptor

@Serializable
data class Datetime(
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
            output.encodeString(Datetime.format(obj))
        }

        override fun deserialize(input: Decoder): Datetime {
            return Datetime.parse(input.decodeString())
        }
    }
}

@Suppress("FunctionName")
expect fun Datetime(timestamp: Long? = null): Datetime

internal expect fun Datetime.Companion.parse(dateString: String): Datetime
internal expect fun Datetime.Companion.format(datetime: Datetime): String

val Datetime.Companion.DHIS_DATE_FORMAT: String
    get() = "yyyy-MM-dd'T'HH:mm:ss.SSS"
