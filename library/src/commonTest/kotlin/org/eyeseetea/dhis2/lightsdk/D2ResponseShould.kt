package org.eyeseetea.dhis2.lightsdk

import org.eyeseetea.dhis2.lightsdk.optionsets.Option
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSet
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class D2ResponseShould {
    @Test
    @kotlin.js.JsName("return_right_type")
    fun `return right type if response is success`() {

        val optionSet = givenAOptionSet()

        val optionSetSuccessResponse = D2Response.Success(optionSet)

        assertTrue(optionSetSuccessResponse.isSuccess)
        assertFalse(optionSetSuccessResponse.isError)

        optionSetSuccessResponse.fold({ fail() },
            { success ->
                success is OptionSet
                assertEquals(optionSet, success)
            })
    }

    @Test
    @kotlin.js.JsName("return_error_type")
    fun `return error type if response is an error`() {

        val networkErrorResponse =
            givenANetworkConnectionError()

        assertTrue(networkErrorResponse is D2Response<OptionSet>)
        assertFalse(networkErrorResponse.isSuccess)
        assertTrue(networkErrorResponse.isError)

        networkErrorResponse.fold(
            { error ->
                error is D2Response.Error.NetworkConnection
                assertEquals(networkErrorResponse, error)
            }, { fail() })
    }

    @Test
    @kotlin.js.JsName("return_mapped_right_type")
    fun `return mapped right type if map a success response`() {

        val optionSet = givenAOptionSet()
        val optionSetSuccessResponse = D2Response.Success(optionSet)

        val mappedResult = optionSetSuccessResponse.map { mapOptionSet(it) }

        mappedResult.fold({ fail() },
            { success ->
                assertTrue(success.name.contains(" mapped"))
            })
    }

    @Test
    @kotlin.js.JsName("return_error_mapping_error_type")
    fun `return error type if map an error response`() {

        val networkErrorResponse = givenANetworkConnectionError()

        val mappedResult = networkErrorResponse.map { mapOptionSet(it) }

        mappedResult.fold(
            { error -> assertEquals(networkErrorResponse, error) }, { fail() })
    }

    @Test
    @kotlin.js.JsName("return_mapped_list_right_type")
    fun `return mapped list right type if map success response list`() {

        val optionSetList = listOf(givenAOptionSet("E1"), givenAOptionSet("E2"))
        val optionSetListSuccessResponse = D2Response.Success(optionSetList)

        val mappedResult =
            optionSetListSuccessResponse.map { it.map { optionSet -> mapOptionSet(optionSet) } }

        mappedResult.fold({ fail() },
            { success ->
                for (movie in success)
                    assertTrue(movie.name.contains(" mapped"))
            })
    }

    @Test
    @kotlin.js.JsName("return_mapped_list_right_type_flat_map")
    fun `return mapped list right type if flatmap success either lists`() {

        val optionSet = givenAOptionSet()
        val optionSetSuccessResponse = D2Response.Success(optionSet)

        val numOptionsResult1 = optionSetSuccessResponse
            .flatMap { returnedOptionSet ->
                getOptionsInOptionSet(returnedOptionSet.id)
                    .map { options -> options.size }
            }

        val numOptionsResult2 = optionSetSuccessResponse
            .flatMap { returnedOptionSet -> getOptionsInOptionSet(returnedOptionSet.id) }
            .map { options -> options.size }

        numOptionsResult1.fold({ fail() },
            { result -> assertEquals(2, result) })

        numOptionsResult2.fold({ fail() },
            { result -> assertEquals(2, result) })
    }

    private fun givenAOptionSet(name: String = "Example OptionSet"): OptionSet =
        OptionSet(name, "UID", name, null, 1)

    private fun mapOptionSet(originalOptionSet: OptionSet) =
        originalOptionSet.copy(name = originalOptionSet.name + " mapped")

    private fun givenANetworkConnectionError() =
        D2Response.Error.NetworkConnection("Network connection")

    private fun getOptionsInOptionSet(id: String): D2Response<List<Option>> {
        val options = listOf(
            Option("name1", "UID1", "name1", "Code1"),
            Option("name2", "UID2", "name2", "Code2")
        )
        return D2Response.Success(options)
    }
}