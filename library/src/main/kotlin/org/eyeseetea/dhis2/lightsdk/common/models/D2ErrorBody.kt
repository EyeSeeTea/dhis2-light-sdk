package org.eyeseetea.dhis2.lightsdk.common.models

data class D2ErrorBody(
    val httpStatus: String,
    val httpStatusCode: Int,
    val status: String,
    val message: String
)