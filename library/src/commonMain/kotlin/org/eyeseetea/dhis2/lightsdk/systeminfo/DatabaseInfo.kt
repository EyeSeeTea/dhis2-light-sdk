package org.eyeseetea.dhis2.lightsdk.systeminfo

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseInfo(
    val spatialSupport: Boolean
)