package org.eyeseetea.dhis2.lightsdk.organisationunits

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.eyeseetea.dhis2.lightsdk.D2Call
import org.eyeseetea.dhis2.lightsdk.D2Endpoint
import org.eyeseetea.dhis2.lightsdk.D2Response

class OrganisationUnitLevelEndpoint internal constructor(
    private val client: HttpClient,
    apiVersion: String
) : D2Endpoint<OrganisationUnitLevelCollection>(apiVersion) {

    fun getAll(): D2Call<List<OrganisationUnitLevel>> {
        return D2Call {
            var response: D2Response<OrganisationUnitLevelCollection> = execute {
                client.get {
                    url { encodedPath = "$apiSegment/organisationUnitLevels" }
                    parameter("paging", false)
                    parameter(
                        "fields", "id,name,displayName,created,lastUpdated,level"
                    )
                }
            }

            response.map { it.organisationUnitLevels }
        }
    }
}
