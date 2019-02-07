package org.eyeseetea.dhis2.lightsdk.attributes

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class Attribute(
    val id: String,
    @Optional val code: String? = null
)