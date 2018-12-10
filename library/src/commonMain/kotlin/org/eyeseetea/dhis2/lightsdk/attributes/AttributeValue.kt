package org.eyeseetea.dhis2.lightsdk.attributes

import kotlinx.serialization.Serializable
import org.eyeseetea.dhis2.lightsdk.common.Datetime

@Serializable
data class AttributeValue(
    val lastUpdated: Datetime,
    val created: Datetime,
    val value: String,
    val attribute: Attribute
)