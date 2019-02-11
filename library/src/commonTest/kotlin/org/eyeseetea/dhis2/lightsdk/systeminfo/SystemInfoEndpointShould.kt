package org.eyeseetea.dhis2.lightsdk.appinfo

import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.error404Response
import org.eyeseetea.dhis2.lightsdk.common.error500Response
import org.eyeseetea.dhis2.lightsdk.common.mocks.api.givenD2MockApi
import org.eyeseetea.dhis2.lightsdk.common.mocks.systemInfoResponse
import org.eyeseetea.dhis2.lightsdk.executePlatformCall
import org.eyeseetea.dhis2.lightsdk.systeminfo.SystemInfo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class SystemInfoEndpointShould {
    companion object {
        private const val ENDPOINT_SEGMENT = "system/info"
    }

    @Test()
    @kotlin.js.JsName("return_expected_system_info")
    fun `return expected systemInfo`() = executePlatformCall {

        val expectedOptionSets = givenExpectedAppInfo()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, systemInfoResponse())

        val response = d2Api.systemInfo().get().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOptionSets, successResponse) })
    }

    @Test()
    @kotlin.js.JsName("return_expected_system_info_api_version")
    fun `return expected systemInfo especifing api version`() = executePlatformCall {

        val expectedOptionSets = givenExpectedAppInfo()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, systemInfoResponse(), apiVersion = "30")

        val response = d2Api.systemInfo().get().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOptionSets, successResponse) })
    }

    @Test
    @kotlin.js.JsName("return_error_401")
    fun `return unauthorized error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error500Response(), 401)

        val response = d2Api.systemInfo().get().suspendExecute()

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

        val response = d2Api.systemInfo().get().suspendExecute()

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

        val response = d2Api.systemInfo().get().suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(404, response.httpStatusCode)
        } else {
            fail()
        }
    }

    private fun givenExpectedAppInfo(): SystemInfo {
        return JSON.nonstrict.parse(SystemInfo.serializer(), systemInfoResponse())
    }
}