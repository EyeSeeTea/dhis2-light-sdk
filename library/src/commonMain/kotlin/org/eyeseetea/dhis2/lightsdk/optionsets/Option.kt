package org.eyeseetea.dhis2.lightsdk.optionsets

import io.ktor.util.date.GMTDate
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val displayName: String,
    val id: String,
    val name: String,
    val created: GMTDate,
    @Optional val code: String? = ""
){
    val d = GMTDate.START
}