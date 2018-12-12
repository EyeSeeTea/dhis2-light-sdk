package org.eyeseetea.dhis2.lightsdk

import io.ktor.client.features.BadResponseStatusException
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import kotlinx.io.IOException
import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.common.logDebug
import org.eyeseetea.dhis2.lightsdk.common.models.D2ErrorBody

open class D2Endpoint<T> {
    suspend fun execute(block: suspend () -> T): D2Response<T> {
        var response: T

        return try {
            response = block()

            response?.let { D2Response.Success(response) }!!
        } catch (e: IOException) {
            D2Response.Error.NetworkConnection(e.message)
        } catch (e: BadResponseStatusException) {
            parseHttpError(e.response)
        }
    }

    private suspend fun parseHttpError(response: HttpResponse): D2Response.Error {
        var d2ErrorBody: D2ErrorBody? = null

        try {
            val errorBody = response.readText()
            d2ErrorBody = JSON.nonstrict.parse(D2ErrorBody.serializer(), errorBody)
        } catch (e: Exception) {
            // do nothing if errorBody body can not be parsed
        }

        logDebug("Error http  ${response.status.value} D2ErrorBody: " + d2ErrorBody.toString())
        return D2Response.Error.HttpError(response.status.value, d2ErrorBody)
    }
}