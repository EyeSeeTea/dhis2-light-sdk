package org.eyeseetea.dhis2.lightsdk.attributes

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import org.eyeseetea.dhis2.lightsdk.common.Datetime

@Serializable
data class AttributeValue(
    @Optional val lastUpdated: Datetime? = null,
    @Optional val created: Datetime? = null,
    val value: String,
    val attribute: Attribute
)