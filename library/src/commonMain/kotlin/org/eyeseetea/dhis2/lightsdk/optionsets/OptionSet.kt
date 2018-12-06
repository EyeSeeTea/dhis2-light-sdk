package org.eyeseetea.dhis2.lightsdk.optionsets

import kotlinx.serialization.Serializable

@Serializable
data class OptionSet(
    val displayName: String,
    val id: String,
    val name: String,
    val options: List<Option>?,
    val version: Int
)