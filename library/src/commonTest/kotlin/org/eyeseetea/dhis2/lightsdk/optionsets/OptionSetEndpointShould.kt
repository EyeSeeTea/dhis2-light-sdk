package org.eyeseetea.dhis2.lightsdk.optionsets

import io.ktor.util.InternalAPI
import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.error401Response
import org.eyeseetea.dhis2.lightsdk.common.error404Response
import org.eyeseetea.dhis2.lightsdk.common.error500Response
import org.eyeseetea.dhis2.lightsdk.common.mocks.api.givenD2MockApi
import org.eyeseetea.dhis2.lightsdk.common.optionSetsResponse
import org.eyeseetea.dhis2.lightsdk.executePlatformCall
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@UseExperimental(InternalAPI::class)
class OptionSetEndpointShould {

    companion object {
        private const val ENDPOINT_SEGMENT = "optionSets"
    }

    @Test()
    @kotlin.js.JsName("return_expected_optionSets")
    fun `return expected optionSets`() = executePlatformCall {
        // val expectedOptionSets = emptyList<OptionSet>()
        val expectedOptionSets = givenExpectedOptionSets()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, optionSetsResponse())

        // val response = D2Response.Success<List<OptionSet>>(emptyList())
        val response = d2Api.optionSets().getAll().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOptionSets, successResponse) })
    }

    @Test()
    @kotlin.js.JsName("return_expected_optionSets_api_version")
    fun `return expected optionSets especifing api version`() = executePlatformCall {
        // val expectedOptionSets = emptyList<OptionSet>()
        val expectedOptionSets = givenExpectedOptionSets()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, optionSetsResponse(), apiVersion = "30")

        // val response = D2Response.Success<List<OptionSet>>(emptyList())
        val response = d2Api.optionSets().getAll().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOptionSets, successResponse) })
    }

    @Test
    @kotlin.js.JsName("return_error_401")
    fun `return unauthorized error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error401Response(), 401)

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
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error500Response(), 500)

        // val response = D2Response.Error.HttpError(500,null)
        val response = d2Api.optionSets().getAll().suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(500, response.httpStatusCode)
        } else {
            fail()
        }
    }

    @Test
    @kotlin.js.JsName("return_error_404")
    fun `return not found error response if api version is invalid`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error404Response(), 404, "25")

        val response = d2Api.optionSets().getAll().suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(404, response.httpStatusCode)
        } else {
            fail()
        }
    }

    private fun givenExpectedOptionSets(): List<OptionSet> {

        val collectionResponse = JSON.nonstrict.parse(
            OptionSetCollection.serializer(), optionSetsResponse()
        )

        return collectionResponse.optionSets
    }
}