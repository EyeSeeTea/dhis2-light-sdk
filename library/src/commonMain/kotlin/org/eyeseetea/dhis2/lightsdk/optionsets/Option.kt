package org.eyeseetea.dhis2.lightsdk.optionsets

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val displayName: String,
    val id: String,
    val name: String,
    @Optional val code: String? = ""
)