package org.eyeseetea.dhis2.lightsdk.organisationunitlevels

import io.ktor.util.InternalAPI
import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.error401Response
import org.eyeseetea.dhis2.lightsdk.common.error404Response
import org.eyeseetea.dhis2.lightsdk.common.error500Response
import org.eyeseetea.dhis2.lightsdk.common.mocks.api.givenD2MockApi
import org.eyeseetea.dhis2.lightsdk.common.organisationUnitLevelsResponse
import org.eyeseetea.dhis2.lightsdk.executePlatformCall
import org.eyeseetea.dhis2.lightsdk.organisationunits.OrganisationUnitLevel
import org.eyeseetea.dhis2.lightsdk.organisationunits.OrganisationUnitLevelCollection
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@UseExperimental(InternalAPI::class)
class OrganisationUnitLevelEndpointShould {

    companion object {
        private const val ENDPOINT_SEGMENT = "organisationUnitLevels"
    }

    @Test()
    @kotlin.js.JsName("return_expected_organisation_unit_levels")
    fun `return expected organisationUnitLevels`() = executePlatformCall {
        val expectedOrgUnitLevels = givenExpectedOrganisationUnitLevels()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, organisationUnitLevelsResponse())

        val response = d2Api.organisationUnitLevels().getAll().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOrgUnitLevels, successResponse) })
    }

    @Test()
    @kotlin.js.JsName("return_expected_organisation_unit_levels_api_version")
    fun `return expected organisationUnitLevels especifing api version`() = executePlatformCall {
        val expectedOrgUnitLevels = givenExpectedOrganisationUnitLevels()

        val d2Api =
            givenD2MockApi(ENDPOINT_SEGMENT, organisationUnitLevelsResponse(), apiVersion = "30")

        val response = d2Api.organisationUnitLevels().getAll().suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOrgUnitLevels, successResponse) })
    }

    @Test
    @kotlin.js.JsName("return_error_401")
    fun `return unauthorized error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error401Response(), 401)

        val response = d2Api.organisationUnitLevels().getAll().suspendExecute()

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

        val response = d2Api.organisationUnitLevels().getAll().suspendExecute()

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

        val response = d2Api.organisationUnitLevels().getAll().suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(404, response.httpStatusCode)
        } else {
            fail()
        }
    }

    private fun givenExpectedOrganisationUnitLevels(): List<OrganisationUnitLevel> {

        val collectionResponse = JSON.nonstrict.parse(
            OrganisationUnitLevelCollection.serializer(), organisationUnitLevelsResponse()
        )

        return collectionResponse.organisationUnitLevels
    }
}
