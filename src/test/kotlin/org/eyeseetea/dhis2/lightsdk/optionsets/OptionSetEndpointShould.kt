package org.eyeseetea.dhis2.lightsdk.optionsets

import com.google.gson.GsonBuilder
import org.amshove.kluent.shouldEqual
import org.eyeseetea.dhis2.lightsdk.D2Api
import org.eyeseetea.dhis2.lightsdk.common.FileReader
import org.eyeseetea.dhis2.lightsdk.common.MockD2ServerRule
import org.eyeseetea.dhis2.lightsdk.common.createD2Gson
import org.eyeseetea.dhis2.lightsdk.common.fromFile
import org.eyeseetea.dhis2.lightsdk.common.models.D2Response
import org.junit.Rule
import org.junit.Test

const val OPTION_SETS_FILE = "optionSets.json"

class OptionSetEndpointShould {

    @Rule
    @JvmField
    val mockD2ServerRule = MockD2ServerRule(FileReader())

    @Test
    fun `return expected optionSets`() {
        val expectedOptionSets = givenExpectedOptionSets()

        mockD2ServerRule.enqueueFileMockResponse(fileName = OPTION_SETS_FILE)

        val d2Api = givenD2Api()

        val fromServerOptionSets = d2Api.optionSets().getAll()

        fromServerOptionSets shouldEqual expectedOptionSets
    }

    private fun givenExpectedOptionSets(): List<OptionSet> {
        val d2Gson = GsonBuilder().createD2Gson()

        val expectedResponse = d2Gson.fromFile<D2Response<OptionSet>>(OPTION_SETS_FILE)

        return expectedResponse?.items!!
    }

    private fun givenD2Api(): D2Api {
        return D2Api.Builder()
            .url(mockD2ServerRule.baseEndpoint)
            .credentials("some credentials", "some pasword")
            .build()
    }
}