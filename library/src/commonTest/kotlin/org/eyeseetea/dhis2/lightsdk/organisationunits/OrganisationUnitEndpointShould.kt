package org.eyeseetea.dhis2.lightsdk.organisationunitlevels

import io.ktor.util.InternalAPI
import kotlinx.serialization.json.JSON
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.error401Response
import org.eyeseetea.dhis2.lightsdk.common.error404Response
import org.eyeseetea.dhis2.lightsdk.common.error500Response
import org.eyeseetea.dhis2.lightsdk.common.mocks.api.givenD2MockApi
import org.eyeseetea.dhis2.lightsdk.common.mocks.organisationUnitsResponse
import org.eyeseetea.dhis2.lightsdk.executePlatformCall
import org.eyeseetea.dhis2.lightsdk.organisationunits.OrganisationUnit
import org.eyeseetea.dhis2.lightsdk.organisationunits.OrganisationUnitCollection
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@UseExperimental(InternalAPI::class)
class OrganisationUnitEndpointShould {

    companion object {
        private const val ENDPOINT_SEGMENT = "organisationUnits"
        private val ORGANISATION_UNIT_IDS = arrayOf("Cf1y95PndAB", "A5HZNrJc2ir", "ijCMzF0Ij9E")
    }

    @Test()
    @JsName("return_expected_organisation_units")
    fun `return expected organisationUnits`() = executePlatformCall {
        val expectedOrgUnits = givenExpectedOrganisationUnits()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, organisationUnitsResponse())

        val response = d2Api.organisationUnits().getAll(ORGANISATION_UNIT_IDS).suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOrgUnits, successResponse) })
    }

    @Test()
    @JsName("return_expected_organisation_units_api_version")
    fun `return expected organisationUnits especifing api version`() = executePlatformCall {
        val expectedOrgUnits = givenExpectedOrganisationUnits()

        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, organisationUnitsResponse(), apiVersion = "30")

        val response = d2Api.organisationUnits().getAll(ORGANISATION_UNIT_IDS).suspendExecute()

        response.fold(
            { error -> fail() },
            { successResponse -> assertEquals(expectedOrgUnits, successResponse) })
    }

    @Test
    @JsName("return_error_401")
    fun `return unauthorized error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error401Response(), 401)

        val response = d2Api.organisationUnits().getAll(ORGANISATION_UNIT_IDS).suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(401, response.httpStatusCode)
        } else {
            fail()
        }
    }

    @Test
    @JsName("return_error_500")
    fun `return internal server error response`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error500Response(), 500)

        val response = d2Api.organisationUnits().getAll(ORGANISATION_UNIT_IDS).suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(500, response.httpStatusCode)
        } else {
            fail()
        }
    }

    @Test
    @JsName("return_error_404")
    fun `return not found error response if api version is invalid`() = executePlatformCall {
        val d2Api = givenD2MockApi(ENDPOINT_SEGMENT, error404Response(), 404, "25")

        val response = d2Api.organisationUnits().getAll(ORGANISATION_UNIT_IDS).suspendExecute()

        if (response.isError && response is D2Response.Error.HttpError) {
            assertEquals(404, response.httpStatusCode)
        } else {
            fail()
        }
    }

    private fun givenExpectedOrganisationUnits(): List<OrganisationUnit> {

        val collectionResponse = JSON.nonstrict.parse(
            OrganisationUnitCollection.serializer(), organisationUnitsResponse()
        )

        return collectionResponse.organisationUnits
    }
}
