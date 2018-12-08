package org.eyeseetea.dhis2.lightsdk.common

import kotlinx.serialization.json.JSON
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DatetimeShould {
    @Test
    @kotlin.js.JsName("parse_from_string")
    fun `parse from dhis2 string format`() {

        val dateString = "2017-09-26T10:02:41.042"
        val datetime = Datetime.parse(dateString)

        assertNotNull(datetime)
    }

    @Test
    @kotlin.js.JsName("format_to_string")
    fun `format to string according to dhis2 string format`() {

        val expectedDateString = "2017-09-26T10:02:41.042"
        val datetime = Datetime.parse(expectedDateString)

        val formattedDateTime = datetime.format()

        assertEquals(expectedDateString, formattedDateTime)
    }

    @Test
    @kotlin.js.JsName("deserialize_from_string")
    fun `deserialize from dhis2 string format`() {

        val dateString = "\"2017-09-26T10:02:41.042\""
        val datetime = JSON.parse(Datetime.serializer(), dateString)

        assertNotNull(datetime)
    }

    @Test
    @kotlin.js.JsName("serialize_to_string")
    fun `serialize to string according to dhis2 string format`() {

        val dateString = "2017-09-26T10:02:41.042"
        val datetime = Datetime.parse(dateString)
        val serializeDatetime = JSON.stringify(Datetime.serializer(), datetime)

        assertEquals("\"$dateString\"", serializeDatetime)
    }
}