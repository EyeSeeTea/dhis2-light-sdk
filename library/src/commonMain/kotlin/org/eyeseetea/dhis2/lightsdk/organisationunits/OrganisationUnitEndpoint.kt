package org.eyeseetea.dhis2.lightsdk.organisationunits

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.eyeseetea.dhis2.lightsdk.D2Call
import org.eyeseetea.dhis2.lightsdk.D2Endpoint
import org.eyeseetea.dhis2.lightsdk.D2Response
import kotlin.js.JsName

class OrganisationUnitEndpoint internal constructor(
    private val client: HttpClient,
    apiVersion: String
) : D2Endpoint<OrganisationUnitCollection>(apiVersion) {

    @JsName("getAll")
    fun getAll(ids: Array<String>): D2Call<List<OrganisationUnit>> {
        return D2Call {
            var response: D2Response<OrganisationUnitCollection> = execute {
                client.get {
                    url { encodedPath = "$apiSegment/organisationUnits" }
                    parameter("paging", false)
                    parameter(
                        "fields", "id,name,displayName,created,lastUpdated," +
                            "path,level,programs[id,attributeValues[*,attribute[id,code]]]," +
                            "attributeValues[*,attribute[id,code]]"
                    )

                    if (!ids.isEmpty()) {
                        parameter("filter", buildIdFilter("id", ids.toList()))
                    }
                }
            }

            response.map { it.organisationUnits }
        }
    }
}
