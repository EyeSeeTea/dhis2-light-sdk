package org.eyeseetea.dhis2.lightsdk.common

import kotlinx.serialization.json.JSON
import kotlin.test.Test
import kotlin.test.assertEquals

class DatetimeShould {
    @Test
    @kotlin.js.JsName("parse_from_string")
    fun `parse from dhis2 string format`() {

        val dateString = "2017-09-26T10:02:41.042"
        val datetime = Datetime.parse(dateString)

        assertEquals(2017, datetime.year)
        assertEquals(8, datetime.month)
        assertEquals(26, datetime.day)
        assertEquals(10, datetime.hour)
        assertEquals(2, datetime.minute)
        assertEquals(41, datetime.second)
        assertEquals(42, datetime.millisecond)
        assertEquals(1506420161042, datetime.timestamp)
    }

    @Test
    @kotlin.js.JsName("format_to_string")
    fun `format to string according to dhis2 string format`() {

        val datetime = Datetime(2017, 8, 26, 10, 2, 41, 42, 1506420161042)

        val formattedDateTime = datetime.format()

        assertEquals("2017-09-26T10:02:41.042", formattedDateTime)
    }

    @Test
    @kotlin.js.JsName("deserialize_from_string")
    fun `deserialize from dhis2 string format`() {

        val dateString = "\"2017-09-26T10:02:41.042\""
        val datetime = JSON.parse(Datetime.serializer(), dateString)

        assertEquals(2017, datetime.year)
        assertEquals(8, datetime.month)
        assertEquals(26, datetime.day)
        assertEquals(10, datetime.hour)
        assertEquals(2, datetime.minute)
        assertEquals(41, datetime.second)
        assertEquals(42, datetime.millisecond)
        assertEquals(1506420161042, datetime.timestamp)
    }

    @Test
    @kotlin.js.JsName("serialize_to_string")
    fun `serialize to string according to dhis2 string format`() {
        val datetime = Datetime(2017, 8, 26, 10, 2, 41, 42, 1506420161042)

        val serializeDatetime = JSON.stringify(Datetime.serializer(), datetime)

        assertEquals("\"2017-09-26T10:02:41.042\"", serializeDatetime)
    }
}