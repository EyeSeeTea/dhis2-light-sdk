package org.eyeseetea.dhis2.lightsdk

import kotlin.test.Test
import kotlin.test.assertEquals

class D2ApiShould {

    @Test
    @kotlin.js.JsName("create_d2api_constructor")
    fun `create D2Api successfully via constructor`() {

        val someUrl = "https://someurl.com"
        val someCredentials =
            D2Credentials("some username", "some password")

        val d2Api = D2Api(someUrl, someCredentials)

        assertEquals(someUrl, d2Api.urlBase)
        assertEquals(someCredentials, d2Api.credentials)
    }

    @Test
    @kotlin.js.JsName("create_d2api_builder")
    fun `create D2Api successfully via builder`() {

        val someUrl = "https://someurl.com"
        val someCredentials =
            D2Credentials("some username", "some password")

        val d2Api = D2Api.Builder()
            .url(someUrl)
            .credentials("some username", "some password")
            .build()

        assertEquals(someUrl, d2Api.urlBase)
        assertEquals(someCredentials, d2Api.credentials)
    }
}