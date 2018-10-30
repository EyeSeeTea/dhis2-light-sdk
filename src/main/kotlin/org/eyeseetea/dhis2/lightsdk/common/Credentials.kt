package org.eyeseetea.dhis2.lightsdk.common

class Credentials(val username: String, val password:String){
    init {
        if (username.isEmpty())
            throw IllegalArgumentException("username can not be empty")

        if (password.isEmpty())
            throw IllegalArgumentException("password can not be empty")
    }
}