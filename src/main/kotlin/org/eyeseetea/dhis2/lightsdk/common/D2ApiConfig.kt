package org.eyeseetea.dhis2.lightsdk.common

data class D2ApiConfig(val url: String, val credentials: D2Credentials){
    init {
        if (url.isBlank())
            throw IllegalArgumentException("url can not be empty")
    }
}