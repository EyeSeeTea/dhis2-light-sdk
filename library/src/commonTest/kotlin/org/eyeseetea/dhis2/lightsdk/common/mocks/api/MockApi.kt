package org.eyeseetea.dhis2.lightsdk.common.mocks.api

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
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray
import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Api

fun givenD2MockApi(endpointSegment: String, responseBody: String, httpStatusCode: Int = 200, apiVersion: String = ""): D2Api {
    val apiSegment = when {
        apiVersion.isBlank() -> "/api"
        else -> "/api/$apiVersion" }

    val httpMockEngine = MockEngine {
        when (url.encodedPath) {
            "$apiSegment/$endpointSegment" -> {
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
            serializer = KotlinxSerializer(JSON.nonstrict)
        }
    }

    return D2Api.Builder()
        .externalClient(client)
        .apiVersion(apiVersion)
        .build()
}