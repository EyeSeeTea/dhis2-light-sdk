package org.eyeseetea.dhis2.lightsdk.useraccount

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.eyeseetea.dhis2.lightsdk.D2Endpoint
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.D2Call

class MeEndpoint internal constructor(
    private val client: HttpClient,
    apiVersion: String
) :
    D2Endpoint<UserAccount>(apiVersion) {

    fun get(): D2Call<UserAccount> {
        return D2Call {
            var response: D2Response<UserAccount> = super.execute {
                client.get {
                    url { encodedPath = "$apiSegment/me" }
                    parameter("paging", false)
                    parameter(
                        "fields", "id,created,lastUpdated,name,displayName," +
                            "userCredentials[username],attributeValues[value,attribute[id,code]]," +
                            " organisationUnits[id, programs[id,programType]]"
                    )
                }
            }

            response
        }
    }
}
