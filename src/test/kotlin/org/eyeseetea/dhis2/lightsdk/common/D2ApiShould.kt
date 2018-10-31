package org.eyeseetea.dhis2.lightsdk.common

import org.amshove.kluent.shouldEqual
import org.eyeseetea.dhis2.lightsdk.D2Api
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.net.MalformedURLException

class D2ApiShould {

    @Rule @JvmField
    var expectedException = ExpectedException.none()

    @Test
    fun `create D2Api successfully via constructor`() {

        val someUrl = "https://someurl.com"
        val someCredentials = D2Credentials("some username", "some password")

        val d2Api = D2Api(someUrl, someCredentials)

        d2Api.url shouldEqual someUrl
        d2Api.credentials shouldEqual someCredentials
    }

    @Test
    fun `throw illegalArgumentException if url is not valid via constructor`() {
        expectedException.expect(MalformedURLException::class.java)

        val someCredentials = D2Credentials("some username", "some password")

        D2Api("Not valid url", someCredentials)
    }

    @Test
    fun `create D2Api successfully via builder`() {

        val someUrl = "https://someurl.com"
        val someCredentials = D2Credentials("some username", "some password")

        val d2Api = D2Api.Builder()
                .url(someUrl)
                .credentials("some username", "some password")
                .build()

        d2Api.url shouldEqual someUrl
        d2Api.credentials shouldEqual someCredentials
    }

    @Test
    fun `throw illegalArgumentException if url and credentials aren't assigned via builder`() {

        expectedException.expect(IllegalArgumentException::class.java)

        D2Api.Builder()
                .build()
    }

    @Test
    fun `throw illegalArgumentException if url is not assigned via builder`() {

        expectedException.expect(IllegalArgumentException::class.java)

        D2Api.Builder()
                .credentials("some username", "some password")
                .build()
    }

    @Test
    fun `throw illegalArgumentException if credentials is not assigned via builder`() {

        expectedException.expect(IllegalArgumentException::class.java)

        D2Api.Builder()
                .url("https://someurl.com")
                .build()
    }
}