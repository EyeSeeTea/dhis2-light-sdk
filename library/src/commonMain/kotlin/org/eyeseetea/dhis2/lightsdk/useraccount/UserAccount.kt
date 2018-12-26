package org.eyeseetea.dhis2.lightsdk.useraccount

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import org.eyeseetea.dhis2.lightsdk.common.Datetime
import org.eyeseetea.dhis2.lightsdk.organisationunits.OrganisationUnit

@Serializable
data class UserAccount(
    val lastUpdated: Datetime,
    val created: Datetime,
    val displayName: String,
    val id: String,
    val name: String,
    val userCredentials: UserCredentials,
    @Optional val organisationUnits: List<OrganisationUnit> = emptyList()
)