package org.eyeseetea.dhis2.lightsdk.optionsets

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.util.InternalAPI
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray
import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Api
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.error500Response
import org.eyeseetea.dhis2.lightsdk.common.models.D2CollectionResponseCustomSerializer
import org.eyeseetea.dhis2.lightsdk.common.models.Pager
import org.eyeseetea.dhis2.lightsdk.common.optionSetsResponse
import org.eyeseetea.dhis2.lightsdk.executePlatformCall
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@UseExperimental(InternalAPI::class)
class OptionSetEndpointShould {

    @Test()
    @kotlin.js.JsName("return_expected_optionSets")
    fun `return expected optionSets`() = executePlatformCall {
        assertTrue(true)

        // val expectedOptionSets = emptyList<OptionSet>()
        val expectedOptionSets = givenExpectedOptionSets()

        val d2Api = givenD2MockApi(optionSetsResponse())

        // val response = D2Response.Success<List<OptionSet>>(emptyList())
        val response = d2Api.optionSets().getAll().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOptionSets, successResponse) })
    }

    @Test
    @kotlin.js.JsName("return_error_401")
    fun `return unauthorized error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(error500Response(), 401)

        // val response = D2Response.Error.HttpError(401,null)
        val response = d2Api.optionSets().getAll().suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(401, response.httpStatusCode)
        } else {
            fail()
        }
    }

    @Test
    @kotlin.js.JsName("return_error_500")
    fun `return internal server error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(error500Response(), 500)

        // val response = D2Response.Error.HttpError(500,null)
        val response = d2Api.optionSets().getAll().suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(500, response.httpStatusCode)
        } else {
            fail()
        }
    }

    private fun givenExpectedOptionSets(): List<OptionSet> {

        val collectionResponse = JSON.nonstrict.parse(
            D2CollectionResponseCustomSerializer(OptionSet.serializer()),
            optionSetsResponse()
        )

        return collectionResponse.items
    }

    private fun givenD2MockApi(responseBody: String, httpStatusCode: Int = 200): D2Api {
        val httpMockEngine = MockEngine {
            when (url.encodedPath) {
                "/api/optionSets" -> {
                    MockHttpResponse(
                        call,
                        HttpStatusCode.fromValue(httpStatusCode),
                        ByteReadChannel(responseBody.toByteArray(Charsets.UTF_8)),
                        headersOf(HttpHeaders.ContentType to listOf(ContentType.Application.Json.toString()))
                    )
                }
                else -> {
                    error("Unhandled ${url.fullPath}")
                }
            }
        }

        val client = HttpClient(httpMockEngine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(JSON.nonstrict).apply {
                    register(D2CollectionResponseCustomSerializer(OptionSet.serializer()))
                    register(OptionSet.serializer())
                    register(Option.serializer())
                    register(Pager.serializer())
                }
            }
        }

        return D2Api.Builder()
            .externalClient(client)
            .build()
    }
}