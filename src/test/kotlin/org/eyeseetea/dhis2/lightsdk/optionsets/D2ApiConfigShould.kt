package org.eyeseetea.dhis2.lightsdk.optionsets

import org.amshove.kluent.shouldBe
import org.eyeseetea.dhis2.lightsdk.common.D2ApiConfig
import org.eyeseetea.dhis2.lightsdk.common.D2Credentials
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class D2ApiConfigShould {

    @Rule @JvmField
    var expectedException = ExpectedException.none()

    @Test
    fun `create D2Config successfully`(){

        val someUrl = "https://someurl.com"
        val someCredentials = D2Credentials("some username", "some password")

        val dhis2ApiConfig = D2ApiConfig(someUrl, someCredentials)

        dhis2ApiConfig.url shouldBe someUrl
        dhis2ApiConfig.credentials shouldBe someCredentials
    }

    @Test
    fun `throw illegalArgumentException if url is empty`(){
        expectedException.expect(IllegalArgumentException::class.java)

        val someCredentials = D2Credentials("some username", "some password")

        D2ApiConfig(" ", someCredentials)
    }
}