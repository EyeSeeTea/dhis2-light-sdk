package org.eyeseetea.dhis2.lightsdk.optionsets

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.eyeseetea.dhis2.lightsdk.D2Endpoint
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.logDebug
import org.eyeseetea.dhis2.lightsdk.common.models.D2CollectionResponse
import org.eyeseetea.dhis2.lightsdk.D2Call

class OptionSetEndpoint internal constructor(private val client: HttpClient) :
    D2Endpoint<D2CollectionResponse>() {

    fun getAll(): D2Call<List<OptionSet>> {
        return D2Call {
            var response: D2Response<D2CollectionResponse> = execute {
                client.get {
                    url { encodedPath = "/api/optionSets" }
                    parameter("paging", false)
                    // parameter("fields","id,name,displayName,created,lastUpdated,access," +
                    //    "version,options[id,name,displayName,created,lastUpdated,access," +
                    // "code,attributeValues[*,attribute[id,code]]]")
                    parameter(
                        "fields", "id,name,displayName,created,lastUpdated,access," +
                                "version,options[id,name,displayName,created,lastUpdated,access," +
                                "code]"
                    )
                }
            }

            logDebug("getAll response: " + response.toString())
            response.map { it.items }
        }

    }
}
