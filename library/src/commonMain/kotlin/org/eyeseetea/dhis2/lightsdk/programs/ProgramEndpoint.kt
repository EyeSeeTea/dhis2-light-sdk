package org.eyeseetea.dhis2.lightsdk.programs

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.eyeseetea.dhis2.lightsdk.D2Call
import org.eyeseetea.dhis2.lightsdk.D2Endpoint
import org.eyeseetea.dhis2.lightsdk.D2Response
import kotlin.js.JsName

class ProgramEndpoint internal constructor(
    private val client: HttpClient,
    apiVersion: String
) : D2Endpoint<ProgramCollection>(apiVersion) {

    @JsName("getAll")
    fun getAll(ids: Array<String>): D2Call<List<Program>> {
        return D2Call {
            var response: D2Response<ProgramCollection> = execute {
                client.get {
                    url { encodedPath = "$apiSegment/programs" }
                    parameter("paging", false)
                    parameter(
                        "fields", "id,name,displayName,created,lastUpdated,programType," +
                            "attributeValues[*,attribute[id,code]]," +
                            "programStages[id,name,displayName,created,lastUpdated," +
                            "programStageDataElements[id,name,displayName,created,lastUpdated," +
                            "sortOrder]programStageSections[id,name,displayName,created," +
                            "lastUpdated,sortOrder]]"
                    )

                    if (!ids.isEmpty()) {
                        parameter("filter", buildIdFilter("id", ids.toList()))
                    }
                }
            }

            response.map { it.programs }
        }
    }
}
