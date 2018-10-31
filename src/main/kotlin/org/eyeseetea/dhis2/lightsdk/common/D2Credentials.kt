package org.eyeseetea.dhis2.lightsdk.common

data class D2Credentials(val username: String, val password: String) {
    init {
        if (username.isBlank())
            throw IllegalArgumentException("username can not be empty")

        if (password.isBlank())
            throw IllegalArgumentException("password can not be empty")
    }
}