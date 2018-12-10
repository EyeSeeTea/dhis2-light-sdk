package org.eyeseetea.dhis2.lightsdk.attributes

import kotlinx.serialization.Serializable

@Serializable
data class Attribute(
    val id: String,
    val code: String
)