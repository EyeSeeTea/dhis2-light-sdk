package org.eyeseetea.dhis2.lightsdk

import junit.framework.Assert.fail
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldContain
import org.eyeseetea.dhis2.lightsdk.optionsets.Option
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSet
import org.junit.Test
import java.io.IOException
import java.util.Date

class D2ResponseShould {
    @Test
    fun `success response should return right type`() {

        val optionSet = givenAOptionSet()

        val optionSetSuccessResponse = D2Response.Success(optionSet)

        optionSetSuccessResponse shouldBeInstanceOf D2Response::class.java
        optionSetSuccessResponse.isSuccess shouldBe true
        optionSetSuccessResponse.isError shouldBe false
        optionSetSuccessResponse.fold({ fail() },
            { success ->
                success shouldBeInstanceOf OptionSet::class.java
                success shouldBe optionSet
            })
    }

    @Test
    fun `error response should return error type`() {

        val networkErrorResponse =
            givenANetworkConnectionError()

        networkErrorResponse shouldBeInstanceOf D2Response::class.java
        networkErrorResponse.isSuccess shouldBe false
        networkErrorResponse.isError shouldBe true
        networkErrorResponse.fold({ error ->
            error shouldBeInstanceOf D2Response.Error.NetworkConnection::class.java
            error shouldBe networkErrorResponse
        }, { fail() })
    }

    @Test
    fun `mapped success response should return mapped right type`() {

        val optionSet = givenAOptionSet()
        val optionSetSuccessResponse = D2Response.Success(optionSet)

        val mappedResult = optionSetSuccessResponse.map { mapOptionSet(it) }

        mappedResult.fold({ fail() },
            { success ->
                success.name shouldContain " mapped"
            })
    }

    @Test
    fun `mapped error response should return error type`() {

        val networkErrorResponse = givenANetworkConnectionError()

        val mappedResult = networkErrorResponse.map { mapOptionSet(it) }

        mappedResult.fold({ error -> error shouldBe networkErrorResponse }, { fail() })
    }

    @Test
    fun `mapped success response list should return mapped list right type`() {

        val optionSetList = listOf(givenAOptionSet("E1"), givenAOptionSet("E2"))
        val optionSetListSuccessResponse = D2Response.Success(optionSetList)

        val mappedResult =
            optionSetListSuccessResponse.map { it.map { optionSet -> mapOptionSet(optionSet) } }

        mappedResult.fold({ fail() },
            { success ->
                for (movie in success)
                    movie.name shouldContain " mapped"
            })
    }

    @Test
    fun `flatmap success either lists should return mapped list right type`() {

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
            { result -> result shouldBe 2 })

        numOptionsResult2.fold({ fail() },
            { result -> result shouldBe 2 })
    }

    private fun givenAOptionSet(name: String = "Example OptionSet"): OptionSet =
        OptionSet(Date(), name, "UID", Date(), name, null, 1)

    private fun mapOptionSet(originalOptionSet: OptionSet) =
        originalOptionSet.copy(name = originalOptionSet.name + " mapped")

    private fun givenANetworkConnectionError() =
        D2Response.Error.NetworkConnection("Network connection", IOException())

    private fun getOptionsInOptionSet(id: String): D2Response<List<Option>> {
        val options = listOf(
            Option(Date(), "name1", "UID1", Date(), "name1", null, "Code1"),
            Option(Date(), "name2", "UID2", Date(), "name2", null, "Code2")
        )
        return D2Response.Success(options)
    }
}