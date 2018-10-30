package org.eyeseetea.dhis2.lightsdk.optionsets

import org.amshove.kluent.shouldBe
import org.eyeseetea.dhis2.lightsdk.common.Dhis2ApiConfig
import org.eyeseetea.dhis2.lightsdk.common.Dhis2Credentials
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class Dhis2ApiConfigShould {

    @Rule @JvmField
    var expectedException = ExpectedException.none()

    @Test
    fun `create Dhis2Config successfully`(){

        val someUrl = "https://someurl.com"
        val someCredentials = Dhis2Credentials("some username", "some password")

        val dhis2ApiConfig = Dhis2ApiConfig(someUrl, someCredentials)

        dhis2ApiConfig.url shouldBe someUrl
        dhis2ApiConfig.credentials shouldBe someCredentials
    }

    @Test
    fun `throw illegalArgumentException if url is empty`(){
        expectedException.expect(IllegalArgumentException::class.java)

        val someCredentials = Dhis2Credentials("some username", "some password")

        Dhis2ApiConfig(" ", someCredentials)
    }
}