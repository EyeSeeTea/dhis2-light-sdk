package org.eyeseetea.dhis2.lightsdk.optionsets

import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.error500Response
import org.eyeseetea.dhis2.lightsdk.executePlatformCall
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

class OptionShould {
    @Test
    @kotlin.js.JsName("parse_with_null_code")
    fun `parse json string with null code`() = executePlatformCall {
        val optionJson = "{\n" +
                    "\"lastUpdated\": \"2017-09-26T10:02:40.880\",\n" +
                    "\"code\": \"\",\n" +
                    "\"created\": \"2017-09-26T10:02:38.820\",\n" +
                    "\"name\": \"SW-Female & Male Sex worker\",\n" +
                    "\"id\": \"bT503k26qdg\",\n" +
                    "\"displayName\": \"SW-Female & Male Sex worker\",\n" +
                "}"

        val option = JSON.nonstrict.parse(Option.serializer(), optionJson)

        assertNotNull(option)
    }

    @Test
    @kotlin.js.JsName("parse_without_code_field")
    fun `parse json string without code field`() = executePlatformCall {
        val optionJson = "{\n" +
                "\"lastUpdated\": \"2017-09-26T10:02:40.880\",\n" +
                "\"created\": \"2017-09-26T10:02:38.820\",\n" +
                "\"name\": \"SW-Female & Male Sex worker\",\n" +
                "\"id\": \"bT503k26qdg\",\n" +
                "\"displayName\": \"SW-Female & Male Sex worker\",\n" +
                "}"

        val option = JSON.nonstrict.parse(Option.serializer(), optionJson)

        assertNotNull(option)
    }
}