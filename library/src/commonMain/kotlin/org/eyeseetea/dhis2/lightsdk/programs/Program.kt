package org.eyeseetea.dhis2.lightsdk.programs

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import org.eyeseetea.dhis2.lightsdk.common.Datetime

@Serializable
data class Program(
    @Optional val lastUpdated: Datetime? = null,
    @Optional val created: Datetime? = null,
    @Optional val displayName: String = "",
    @Optional val id: String = "",
    @Optional val name: String = "",
    @Optional val programType: String = ""
)