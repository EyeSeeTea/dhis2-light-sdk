package org.eyeseetea.dhis2.lightsdk.common

data class Dhis2ApiConfig(val url: String, val credentials: Dhis2Credentials){
    init {
        if (url.isBlank())
            throw IllegalArgumentException("url can not be empty")
    }
}