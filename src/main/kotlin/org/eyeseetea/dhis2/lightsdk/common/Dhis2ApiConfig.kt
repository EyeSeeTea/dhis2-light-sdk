package org.eyeseetea.dhis2.lightsdk.common

data class Dhis2ApiConfig(val url: String, val credentials: Credentials){
    init {
        if (url.isEmpty())
            throw IllegalArgumentException("url can not be empty")
    }
}