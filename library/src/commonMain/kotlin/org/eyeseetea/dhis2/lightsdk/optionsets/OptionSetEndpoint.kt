package org.eyeseetea.dhis2.lightsdk.optionsets

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.eyeseetea.dhis2.lightsdk.D2Endpoint
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.D2Call

class OptionSetEndpoint internal constructor(private val client: HttpClient) :
    D2Endpoint<OptionSetCollection>() {

    fun getAll(): D2Call<List<OptionSet>> {
        return D2Call {
            var response: D2Response<OptionSetCollection> = execute {
                client.get {
                    url { encodedPath = "/api/optionSets" }
                    parameter("paging", false)
                    parameter(
                        "fields", "id,name,displayName,created,lastUpdated," +
                            "version,options[id,name,displayName,created,lastUpdated," +
                            "code,attributeValues[*,attribute[id,code]]]"
                    )
                }
            }

            response.map { it.optionSets }
        }
    }
}
