package org.eyeseetea.dhis2.lightsdk.organisationunits

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import org.eyeseetea.dhis2.lightsdk.common.models.Pager

// TODO: Important!! this collection type classes are temporal because on the future
// we should use D2CollectionResponse, see comments on these file
@Serializable
class OrganisationUnitCollection(
    @Optional val pager: Pager? = null,
    var organisationUnits: List<OrganisationUnit>
)