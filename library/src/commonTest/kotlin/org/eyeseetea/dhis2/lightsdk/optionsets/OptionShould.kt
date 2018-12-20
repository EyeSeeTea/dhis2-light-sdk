package org.eyeseetea.dhis2.lightsdk.optionsets

import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.common.readFile
import kotlin.test.Test
import kotlin.test.assertNotNull

class OptionShould {
    @Test
    @kotlin.js.JsName("parse_with_null_code")
    fun `parse json string with null code`() {
        val file = readFile("option_without_code.json")
        val option = JSON.nonstrict.parse(Option.serializer(), file)
        assertNotNull(option)
    }

    @Test
    @kotlin.js.JsName("parse_without_code_field")
    fun `parse json string without code field`() {
        val file = readFile("option.json")
        val option = JSON.nonstrict.parse(Option.serializer(), file)

        assertNotNull(option)
    }
}