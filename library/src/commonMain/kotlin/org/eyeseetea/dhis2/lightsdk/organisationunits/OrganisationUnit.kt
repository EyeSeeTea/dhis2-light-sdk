package org.eyeseetea.dhis2.lightsdk.organisationunits

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import org.eyeseetea.dhis2.lightsdk.common.Datetime
import org.eyeseetea.dhis2.lightsdk.programs.Program

@Serializable
data class OrganisationUnit(
    @Optional val lastUpdated: Datetime? = null,
    @Optional val created: Datetime? = null,
    @Optional val displayName: String = "",
    @Optional val id: String = "",
    @Optional val name: String = "",
    @Optional val programs: List<Program> = emptyList()
)