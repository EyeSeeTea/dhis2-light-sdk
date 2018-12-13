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
import org.eyeseetea.dhis2.lightsdk.common.LogFeature
import org.eyeseetea.dhis2.lightsdk.common.models.Pager
import org.eyeseetea.dhis2.lightsdk.optionsets.Option
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSet
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSetCollection
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSetEndpoint
expect fun <T> executePlatformCall(block: suspend () -> T): T

@UseExperimental(InternalAPI::class)
class D2Api(
    val urlBase: String,
    val credentials: D2Credentials = D2Credentials.empty(),
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

    fun optionSets(): OptionSetEndpoint {
        return OptionSetEndpoint(client)
    }

    class Builder {
        private var url: String = "http://localhost"
        private var credentials: D2Credentials = D2Credentials("", "")
        private var externalClient: HttpClient? = null

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

        fun build(): D2Api {
            return D2Api(this.url, this.credentials, this.externalClient)
        }
    }
}