package org.eyeseetea.dhis2.lightsdk.optionsets

import kotlinx.serialization.Serializable
import org.eyeseetea.dhis2.lightsdk.common.Datetime

@Serializable
data class OptionSet(
    val lastUpdated: Datetime,
    val created: Datetime,
    val displayName: String,
    val id: String,
    val name: String,
    val options: List<Option>?,
    val version: Int
)