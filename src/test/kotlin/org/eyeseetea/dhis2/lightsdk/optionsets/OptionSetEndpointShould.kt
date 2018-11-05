package org.eyeseetea.dhis2.lightsdk.optionsets

import com.google.gson.GsonBuilder
import junit.framework.Assert.fail
import org.amshove.kluent.shouldEqual
import org.eyeseetea.dhis2.lightsdk.D2Api
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.FileReader
import org.eyeseetea.dhis2.lightsdk.common.MockD2ServerRule
import org.eyeseetea.dhis2.lightsdk.common.createD2Gson
import org.eyeseetea.dhis2.lightsdk.common.fromFile
import org.eyeseetea.dhis2.lightsdk.common.models.D2CollectionResponse
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class OptionSetEndpointShould {
    @Rule
    @JvmField
    val mockD2ServerRule = MockD2ServerRule(FileReader())

    @Test
    fun `return expected optionSets`() {
        val expectedOptionSets = givenExpectedOptionSets()

        mockD2ServerRule.enqueueFileMockResponse(fileName = OPTION_SETS_FILE)

        val d2Api = givenD2Api()

        val response = d2Api.optionSets().getAll()

        response.fold(
            { error -> fail() },
            { successResponse -> successResponse shouldEqual expectedOptionSets })
    }

    @Test
    fun `return unauthorized error response`() {
        mockD2ServerRule.enqueueFileMockResponse(401, fileName = UNAUTHORIZED_ERROR_FILE)

        val d2Api = givenD2Api()

        val response = d2Api.optionSets().getAll()

        when (response) {
            is D2Response.Success -> handleSuccess(response.value)
            is D2Response.Error -> handleError(response)
        }

        if (response.isError && response is D2Response.Error.HttpError) {
            response.httpStatusCode shouldEqual 401
        } else {
            Assert.fail()
        }
    }

    private fun handleError(response: D2Response.Error) {
    }

    private fun handleSuccess(response: List<OptionSet>) {
    }

    @Test
    fun `return internal server error response`() {
        mockD2ServerRule.enqueueFileMockResponse(500, fileName = INTERNAL_SERVER_ERROR_FILE)

        val d2Api = givenD2Api()

        val response = d2Api.optionSets().getAll()

        if (response.isError && response is D2Response.Error.HttpError) {
            response.httpStatusCode shouldEqual 500
        } else {
            Assert.fail()
        }
    }

    private fun givenExpectedOptionSets(): List<OptionSet> {
        val d2Gson = GsonBuilder().createD2Gson()

        val expectedResponse = d2Gson.fromFile<D2CollectionResponse<OptionSet>>(OPTION_SETS_FILE)

        return expectedResponse?.items!!
    }

    private fun givenD2Api(): D2Api {
        return D2Api.Builder()
            .url(mockD2ServerRule.baseEndpoint)
            .credentials("some credentials", "some pasword")
            .build()
    }

    companion object {
        private const val OPTION_SETS_FILE = "OptionSets.json"
        private const val UNAUTHORIZED_ERROR_FILE = "Error401.html"
        private const val INTERNAL_SERVER_ERROR_FILE = "Error500.json"
    }
}