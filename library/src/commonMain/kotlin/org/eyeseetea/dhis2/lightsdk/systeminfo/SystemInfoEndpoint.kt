package org.eyeseetea.dhis2.lightsdk.systeminfo

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.eyeseetea.dhis2.lightsdk.D2Call
import org.eyeseetea.dhis2.lightsdk.D2Endpoint

class SystemInfoEndpoint internal constructor(
    private val client: HttpClient,
    apiVersion: String
) : D2Endpoint<SystemInfo>(apiVersion) {

    fun get(): D2Call<SystemInfo> {
        return D2Call {
            execute {
                client.get {
                    url { encodedPath = "$apiSegment/system/info" }
                }
            }
        }
    }
}
