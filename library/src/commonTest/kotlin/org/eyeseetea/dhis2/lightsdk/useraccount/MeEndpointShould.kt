package org.eyeseetea.dhis2.lightsdk.useraccount

import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.error404Response
import org.eyeseetea.dhis2.lightsdk.common.error500Response
import org.eyeseetea.dhis2.lightsdk.common.mocks.api.givenD2MockApi
import org.eyeseetea.dhis2.lightsdk.common.mocks.meResponse
import org.eyeseetea.dhis2.lightsdk.executePlatformCall
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class MeEndpointShould {
    companion object {
        private const val ENDPOINT_SEGMENT = "me"
    }

    @Test()
    @kotlin.js.JsName("return_expected_me")
    fun `return expected me user account`() = executePlatformCall {

        val expectedMe = givenExpectedMeUserAccount()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, meResponse())

        val response = d2Api.me().get().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedMe, successResponse) })
    }

    @Test()
    @kotlin.js.JsName("return_expected_me_api_version")
    fun `return expected me user account api version`() = executePlatformCall {

        val expectedMe = givenExpectedMeUserAccount()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, meResponse(), apiVersion = "30")

        val response = d2Api.me().get().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedMe, successResponse) })
    }

    @Test
    @kotlin.js.JsName("return_error_401")
    fun `return unauthorized error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error500Response(), 401)

        val response = d2Api.me().get().suspendExecute()

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

        val response = d2Api.me().get().suspendExecute()

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

        val response = d2Api.me().get().suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(404, response.httpStatusCode)
        } else {
            fail()
        }
    }

    private fun givenExpectedMeUserAccount(): UserAccount {
        return JSON.nonstrict.parse(UserAccount.serializer(), meResponse())
    }
}