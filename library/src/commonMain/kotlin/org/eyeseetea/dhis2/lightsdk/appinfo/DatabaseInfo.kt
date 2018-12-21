package org.eyeseetea.dhis2.lightsdk.appinfo

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseInfo(
    val spatialSupport: Boolean
)