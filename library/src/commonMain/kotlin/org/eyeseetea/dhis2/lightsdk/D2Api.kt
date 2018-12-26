package org.eyeseetea.dhis2.lightsdk

import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.header
import io.ktor.http.takeFrom
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64
import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.systeminfo.SystemInfoEndpoint
import org.eyeseetea.dhis2.lightsdk.common.LogFeature
import org.eyeseetea.dhis2.lightsdk.common.models.Pager
import org.eyeseetea.dhis2.lightsdk.optionsets.Option
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSet
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSetCollection
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSetEndpoint
import org.eyeseetea.dhis2.lightsdk.useraccount.MeEndpoint

@UseExperimental(InternalAPI::class)
class D2Api(
    val urlBase: String,
    val credentials: D2Credentials = D2Credentials.empty(),
    val apiVersion: String = "",
    externalClient: HttpClient? = null
) {
    private val client: HttpClient

    init {
        if (externalClient != null)
            client = externalClient
        else {
            client = HttpClient() {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(JSON.nonstrict).apply {
                        // register(D2CollectionResponseCustomSerializer(OptionSet.serializer()))
                        register(OptionSetCollection.serializer())
                        register(OptionSet.serializer())
                        register(Option.serializer())
                        register(Pager.serializer())
                    }
                }

                install(LogFeature)

                defaultRequest {
                    url {
                        takeFrom(urlBase)
                    }
                    if (credentials != D2Credentials.empty()) {
                        header(
                            "Authorization", "Basic " +
                                    "${credentials.username}:${credentials.password}".encodeBase64()
                        )
                    }
                }
            }
        }
    }

    fun systemInfo(): SystemInfoEndpoint {
        return SystemInfoEndpoint(client, apiVersion)
    }

    fun optionSets(): OptionSetEndpoint {
        return OptionSetEndpoint(client, apiVersion)
    }

    fun me(): MeEndpoint {
        return MeEndpoint(client, apiVersion)
    }

    class Builder {
        private var url: String = "http://localhost"
        private var credentials: D2Credentials = D2Credentials("", "")
        private var externalClient: HttpClient? = null
        private var apiVersion = ""

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun credentials(username: String, password: String): Builder {
            this.credentials = D2Credentials(username, password)
            return this
        }

        fun externalClient(externalClient: HttpClient): Builder {
            this.externalClient = externalClient
            return this
        }

        fun apiVersion(apiVersion: String): Builder {
            this.apiVersion = apiVersion
            return this
        }

        fun build(): D2Api {
            return D2Api(this.url, this.credentials, this.apiVersion, this.externalClient)
        }
    }
}