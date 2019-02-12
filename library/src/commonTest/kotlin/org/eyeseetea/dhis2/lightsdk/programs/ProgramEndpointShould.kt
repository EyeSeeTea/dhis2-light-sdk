package org.eyeseetea.dhis2.lightsdk.programs

import io.ktor.util.InternalAPI
import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.error401Response
import org.eyeseetea.dhis2.lightsdk.common.error404Response
import org.eyeseetea.dhis2.lightsdk.common.error500Response
import org.eyeseetea.dhis2.lightsdk.common.mocks.api.givenD2MockApi
import org.eyeseetea.dhis2.lightsdk.common.mocks.responses.programsResponse
import org.eyeseetea.dhis2.lightsdk.executePlatformCall
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@UseExperimental(InternalAPI::class)
class ProgramEndpointShould {

    companion object {
        private const val ENDPOINT_SEGMENT = "programs"
        private val PROGRAM_IDS = arrayOf("ggxhuvMBtxj", "u6pCofOY3O7")
    }

    @Test()
    @kotlin.js.JsName("return_expected_programs")
    fun `return expected programs`() = executePlatformCall {
        val expectedPrograms = givenExpectedPrograms()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, programsResponse())

        val response = d2Api.programs().getAll(PROGRAM_IDS).suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedPrograms, successResponse) })
    }

    @Test()
    @kotlin.js.JsName("return_expected_programs_api_version")
    fun `return expected programs especifing api version`() = executePlatformCall {
        val expectedPrograms = givenExpectedPrograms()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, programsResponse(), apiVersion = "30")

        val response = d2Api.programs().getAll(PROGRAM_IDS).suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedPrograms, successResponse) })
    }

    @Test
    @kotlin.js.JsName("return_error_401")
    fun `return unauthorized error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error401Response(), 401)

        // val response = D2Response.Error.HttpError(401,null)
        val response = d2Api.programs().getAll(PROGRAM_IDS).suspendExecute()

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
        val response = d2Api.programs().getAll(PROGRAM_IDS).suspendExecute()

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

        val response = d2Api.programs().getAll(PROGRAM_IDS).suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(404, response.httpStatusCode)
        } else {
            fail()
        }
    }

    private fun givenExpectedPrograms(): List<Program> {

        val collectionResponse = JSON.nonstrict.parse(
            ProgramCollection.serializer(), programsResponse()
        )

        return collectionResponse.programs
    }
}
