package org.eyeseetea.dhis2.lightsdk.optionsets

import org.amshove.kluent.shouldBe
import org.eyeseetea.dhis2.lightsdk.common.Dhis2Credentials
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class Dhis2CredentialsShould {

    @Rule @JvmField
    var expectedException = ExpectedException.none()

    @Test
    fun `create Dhis2Credentials successfully`(){

        val someUsername = "some username"
        val somePassword = "some password"

        val credentials = Dhis2Credentials(someUsername,somePassword)

        credentials.username shouldBe someUsername
        credentials.password shouldBe somePassword
    }

    @Test
    fun `throw illegalArgumentException if username is empty`(){
        expectedException.expect(IllegalArgumentException::class.java)

        Dhis2Credentials("","some password")
    }

    @Test
    fun `throw illegalArgumentException if password is empty`(){
        expectedException.expect(IllegalArgumentException::class.java)

        Dhis2Credentials("some username","")
    }
}